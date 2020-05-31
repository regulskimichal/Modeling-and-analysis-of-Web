package pl.pwr.maw.measurement

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import org.springframework.web.util.UriComponentsBuilder
import pl.pwr.maw.model.PageSpeedMeasurement
import pl.pwr.maw.model.PageSpeedSetting
import pl.pwr.maw.model.ResultType.*
import pl.pwr.maw.model.pagespeed.PagespeedApiPagespeedResponseV5
import java.time.Instant

@Service
@Transactional(readOnly = true)
class PageSpeedMeasurer(
    @Value("\${api.pageSpeedInsightsUrl}") private val pageSpeedInsightsUrl: String,
    private val webClient: WebClient,
    private val objectMapper: ObjectMapper
) : PerformanceMeasurer<PageSpeedSetting> {

    override fun preformMeasurement(setting: PageSpeedSetting): PageSpeedMeasurement {
        val url = UriComponentsBuilder.fromHttpUrl(pageSpeedInsightsUrl)
            .queryParam("key", setting.apiKey)
            .queryParam("url", setting.pageUrl)
            .apply {
                if (setting.strategy != null) {
                    queryParam("strategy", setting.strategy)
                }
            }.build().toUri()

        return webClient.get().uri(url).exchange()
            .flatMap { it.bodyToMono<PagespeedApiPagespeedResponseV5>() }
            .map { it.asMeasurement(setting) }
            .block()!!
    }

    private fun PagespeedApiPagespeedResponseV5.asMeasurement(setting: PageSpeedSetting): PageSpeedMeasurement {
        return when (lighthouseResult.runtimeError) {
            null -> PageSpeedMeasurement(
                null,
                setting,
                objectMapper.writeValueAsString(this),
                SUCCESS,
                setting.strategy,
                lighthouseResult.userAgent,
                Instant.parse(lighthouseResult.fetchTime),
                lighthouseResult.audits["largest-contentful-paint"]?.numericValue,
                lighthouseResult.audits["first-meaningful-paint"]?.numericValue
            )

            else -> PageSpeedMeasurement(
                null,
                setting,
                objectMapper.writeValueAsString(this),
                API_ERROR,
                null,
                null,
                Instant.now(),
                null,
                null
            )
        }
    }

}
