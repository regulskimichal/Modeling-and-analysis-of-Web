package pl.pwr.maw.measurement

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
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
                setting.configuration?.let {
                    queryParam("location", "${it.location}:${it.browser}.${it.connectivityProfile.value}")
                }
            }
            .build().toUri()
    }

    private fun WebPageTestResponse.asMeasurement(
        originalJson: String,
        setting: WebPageTestSetting
    ): WebPageTestMeasurement {
        if (statusCode == HttpStatus.OK.value() && data != null) {
            val data = objectMapper.treeToValue<WebPageTestResponseData>(data)!!
            val requestHeaders = data.runs["1"]["firstView"]["requests"][0]["headers"]["request"] as ArrayNode
            val userAgent = requestHeaders.map { it.asText() }.find { it.contains("user-agent", ignoreCase = true) }

            return WebPageTestMeasurement(
                resultType = ResultType.SUCCESS,
                userAgent = userAgent,
                analysisTime = data.completed,
                statusCode = statusCode,
                version = webPagetestVersion
            ).apply {
                this.setting = setting
                this.originalResponse = Response(value = originalJson)
            }
        } else {
            return WebPageTestMeasurement(
                resultType = ResultType.API_ERROR,
                statusCode = statusCode,
                version = webPagetestVersion
            ).apply {
                this.setting = setting
                this.originalResponse = Response(value = originalJson)
            }
        }
    }

    companion object {
        val log by logger<WebPageTestMeasurer>()
    }

}
