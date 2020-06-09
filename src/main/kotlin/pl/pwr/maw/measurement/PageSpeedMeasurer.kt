package pl.pwr.maw.measurement

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.util.UriComponentsBuilder
import pl.pwr.maw.commons.logger
import pl.pwr.maw.model.PageSpeedMeasurement
import pl.pwr.maw.model.PageSpeedSetting
import pl.pwr.maw.model.Response
import pl.pwr.maw.model.ResultType.*
import pl.pwr.maw.model.pagespeed.PagespeedApiPagespeedResponseV5
import java.net.URI
import java.time.Instant

@Service
@Transactional(readOnly = true)
class PageSpeedMeasurer(
    @Value("\${api.pageSpeedInsightsUrl}") private val pageSpeedInsightsUrl: String,
    private val webClient: WebClient,
    private val objectMapper: ObjectMapper
) : PerformanceMeasurer<PageSpeedSetting> {

    override suspend fun preformMeasurement(setting: PageSpeedSetting): PageSpeedMeasurement? {
        val url = buildUrl(setting)
        return preformMeasurement(url, setting)
    }

    private fun buildUrl(setting: PageSpeedSetting) = UriComponentsBuilder.fromHttpUrl(pageSpeedInsightsUrl)
        .queryParam("url", setting.pageUrl)
        .apply {
            setting.apiKey?.apiKey?.let {
                queryParam("key", it)
            }

            setting.strategy?.let {
                queryParam("strategy", it)
            }
        }.build().toUri()

    private suspend fun preformMeasurement(url: URI, setting: PageSpeedSetting): PageSpeedMeasurement? {
        return try {
            val clientResponse = webClient.get().uri(url).exchange().awaitSingle()
            val json = clientResponse.awaitBody<String>()
            val body = objectMapper.readValue<PagespeedApiPagespeedResponseV5>(json)
            body.asMeasurement(json, setting)
        } catch (e: Exception) {
            log.error("Measurement failed", e)
            null
        }
    }

    private fun PagespeedApiPagespeedResponseV5.asMeasurement(originalJson: String, setting: PageSpeedSetting) =
        if (lighthouseResult != null && lighthouseResult.runtimeError == null) {
            PageSpeedMeasurement(
                null,
                SUCCESS,
                lighthouseResult.userAgent,
                Instant.parse(lighthouseResult.fetchTime),
                setting.strategy,
                lighthouseResult.audits["first-contentful-paint"]?.numericValue?.toInt(),
                lighthouseResult.audits["first-meaningful-paint"]?.numericValue?.toInt(),
                lighthouseResult.audits["largest-contentful-paint"]?.numericValue?.toInt(),
                lighthouseResult.audits["max-potential-fid"]?.numericValue?.toInt(),
                lighthouseResult.audits["speed-index"]?.numericValue?.toDouble()
            ).apply {
                this.setting = setting
                this.originalResponse = Response(value = originalJson)
            }
        } else {
            PageSpeedMeasurement(
                null,
                API_ERROR
            ).apply {
                this.setting = setting
                this.originalResponse = Response(value = originalJson)
            }
        }

    companion object {
        val log by logger<PageSpeedMeasurer>()
    }

}
