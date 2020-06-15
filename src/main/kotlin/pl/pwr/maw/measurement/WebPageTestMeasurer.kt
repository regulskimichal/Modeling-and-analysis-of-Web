package pl.pwr.maw.measurement

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.treeToValue
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.time.delay
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.util.UriComponentsBuilder
import pl.pwr.maw.commons.logger
import pl.pwr.maw.model.Response
import pl.pwr.maw.model.ResultType
import pl.pwr.maw.model.WebPageTestMeasurement
import pl.pwr.maw.model.WebPageTestSetting
import pl.pwr.maw.model.webpagetest.WebPageTestInitResponse
import pl.pwr.maw.model.webpagetest.WebPageTestResponse
import pl.pwr.maw.model.webpagetest.WebPageTestResponseData
import java.net.URI
import java.time.Duration

@Service
class WebPageTestMeasurer(
    @Value("\${api.webPageTestUrl}") private val webPageTestUrl: String,
    private val webClient: WebClient,
    private val objectMapper: ObjectMapper
) : PerformanceMeasurer<WebPageTestSetting> {

    override suspend fun preformMeasurement(setting: WebPageTestSetting): WebPageTestMeasurement? {
        val webPageTestInitResponse = webClient.get()
            .uri(buildUrl(setting))
            .exchange()
            .awaitSingle()
            .awaitBody<WebPageTestInitResponse>()

        return flow {
            val json = webClient.get()
                .uri(webPageTestInitResponse.data.jsonUrl)
                .exchange()
                .awaitSingle()
                .awaitBody<String>()
            val response = objectMapper.readValue<WebPageTestResponse>(json)

            val code = HttpStatus.valueOf(response.statusCode)
            log.debug("Fetched result from WebPageTest API. Code: $code")

            when {
                code.is1xxInformational -> throw NotReadyException()
                code.is2xxSuccessful -> emit(json to response)
                else -> throw WebPageTestApiException(json, response)
            }
        }.retryWhen { cause, attempt ->
            delay(Duration.ofSeconds(30))
            log.debug("Retrying... Attempt: ${attempt + 1}, cause: $cause")
            cause is NotReadyException && attempt < 20
        }.map { (json, response) ->
            response.asMeasurement(json, setting)
        }.catch { e ->
            if (e is WebPageTestApiException) {
                emit(e.response.asMeasurement(e.json, setting))
            } else {
                log.error(e.message, e)
            }
        }.flowOn(IO).singleOrNull()
    }

    private fun buildUrl(setting: WebPageTestSetting): URI {
        if (setting.apiKey == null) {
            throw InvalidApiKeyException()
        }

        return UriComponentsBuilder.fromHttpUrl(webPageTestUrl)
            .queryParam("url", setting.pageUrl)
            .queryParam("k", setting.apiKey?.apiKey)
            .queryParam("f", "json")
            .apply {
                setting.browserConfiguration?.let {
                    queryParam("location", "${it.location}:${it.browser}.${it.connectivityProfile.value}")
                }
            }
            .build().toUri()
    }

    private fun WebPageTestResponse.asMeasurement(
        originalJson: String,
        setting: WebPageTestSetting
    ): WebPageTestMeasurement {
        val measurement = if (statusCode == HttpStatus.OK.value() && data != null) {
            successfulMeasurement(data)
        } else {
            unsuccessfulMeasurement()
        }

        return measurement.apply {
            this.originalResponse = Response(value = originalJson)
            this.setting = setting
        }
    }

    private fun WebPageTestResponse.successfulMeasurement(objectNode: ObjectNode): WebPageTestMeasurement {
        val data = objectMapper.treeToValue<WebPageTestResponseData>(objectNode)!!
        val firstView = data.runs["1"]["firstView"]
        val requestHeaders = objectMapper.treeToValue<List<String>>(firstView["requests"][0]["headers"]["request"])
        val userAgent = requestHeaders?.find { it.contains("user-agent", ignoreCase = true) }

        return WebPageTestMeasurement(
            resultType = ResultType.SUCCESS,
            userAgent = userAgent,
            analysisTime = data.completed,
            idxml = data.id,
            loadTime = firstView["loadTime"].asDouble().toInt(),
            ttfb = firstView["TTFB"].asDouble().toInt(),
            domStart = firstView["domContentLoadedEventStart"].asDouble().toInt(),
            domEnd = firstView["domContentLoadedEventEnd"].asDouble().toInt(),
            render = firstView["render"].asDouble().toInt(),
            visualComplete = firstView["visualComplete"].asDouble().toInt(),
            fullyLoaded = firstView["fullyLoaded"].asDouble().toInt(),
            requests = firstView["requestsFull"].asDouble().toInt(),
            firstPaint = firstView["firstPaint"].asDouble().toInt(),
            speedIndex = firstView["SpeedIndex"].asDouble().toInt(),
            domElements = firstView["domElements"].asDouble().toInt(),
            statusCode = statusCode,
            version = webPagetestVersion
        )
    }

    private fun WebPageTestResponse.unsuccessfulMeasurement(): WebPageTestMeasurement = WebPageTestMeasurement(
        resultType = ResultType.API_ERROR,
        statusCode = statusCode,
        version = webPagetestVersion
    )

    companion object {
        val log by logger<WebPageTestMeasurer>()
    }

}
