package pl.pwr.maw.measurement

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.util.UriComponentsBuilder
import pl.pwr.maw.model.PageSpeedMeasurement
import pl.pwr.maw.model.PageSpeedSetting
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

    override suspend fun preformMeasurement(setting: PageSpeedSetting): PageSpeedMeasurement {
        val url = buildUrl(setting)
        return preformMeasurement(url, setting)
    }

    private fun buildUrl(setting: PageSpeedSetting) = UriComponentsBuilder.fromHttpUrl(pageSpeedInsightsUrl)
        .queryParam("url", setting.pageUrl)
        .apply {
            if (setting.apiKey.apiKey != null) {
                queryParam("key", setting.apiKey.apiKey)
            }

            if (setting.strategy != null) {
                queryParam("strategy", setting.strategy)
            }
        }.build().toUri()

    private suspend fun preformMeasurement(url: URI, setting: PageSpeedSetting): PageSpeedMeasurement {
        val clientResponse = webClient.get().uri(url).exchange().awaitSingle()
        val body = clientResponse.awaitBody<PagespeedApiPagespeedResponseV5>()
        return body.asMeasurement(setting)
    }

    private fun PagespeedApiPagespeedResponseV5.asMeasurement(setting: PageSpeedSetting): PageSpeedMeasurement {
        return when (lighthouseResult.runtimeError) {
            null -> PageSpeedMeasurement(
                null,
                objectMapper.writeValueAsString(this),
                SUCCESS,
                setting.strategy,
                lighthouseResult.userAgent,
                Instant.parse(lighthouseResult.fetchTime),
                lighthouseResult.audits["largest-contentful-paint"]?.numericValue,
                lighthouseResult.audits["first-meaningful-paint"]?.numericValue
            ).apply { this.setting = setting }

            else -> PageSpeedMeasurement(
                null,
                objectMapper.writeValueAsString(this),
                API_ERROR,
                null,
                null,
                Instant.now(),
                null,
                null
            ).apply { this.setting = setting }
        }
    }

}
