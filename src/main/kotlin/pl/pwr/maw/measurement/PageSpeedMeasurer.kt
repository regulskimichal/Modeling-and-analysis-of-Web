package pl.pwr.maw.measurement

import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import org.springframework.web.util.UriComponentsBuilder
import pl.pwr.maw.apikey.ApiSettingsService
import pl.pwr.maw.measurementsettings.Api
import pl.pwr.maw.measurementsettings.Strategy
import pl.pwr.maw.model.pagespeed.PagespeedApiPagespeedResponseV5

@Service
class PageSpeedMeasurer(
    @Value("\${pageSpeedInsightsUrl}") private val pageSpeedInsightsUrl: String,
    private val webClient: WebClient,
    private val measurementRepository: MeasurementRepository,
    private val apiSettingsService: ApiSettingsService
) : PerformanceMeasurer {

    /*suspend fun getResults(url: String): Measurement? {
        val uri = UriComponentsBuilder.fromHttpUrl(pageSpeedInsightsUrl)
            .queryParam("key", apiSettingsService.getApiKey(Api.PAGE_SPEED))
            .queryParam("url", url)
            .build().toUri()

        return webClient.get().uri(uri).exchange()
            .flatMap { it.bodyToMono<PagespeedApiPagespeedResponseV5>() }
            .flatMap { measurementRepository.save(it.asMeasurement()) }
            .awaitSingle()
    }

    private fun PagespeedApiPagespeedResponseV5.asMeasurement(): Measurement {
        val error = this.lighthouseResult?.runtimeError

        val resultType: ResultType = if (error == null) {
            ResultType.SUCCESS
        } else {
            if (error.code == "FAILED_DOCUMENT_REQUEST") {
                ResultType.FAILURE
            } else {
                ResultType.WITH_ERRORS
            }
        }

        val loadingTime = this.loadingExperience?.metrics?.get("FIRST_INPUT_DELAY_MS")?.distributions?.sumByDouble {
            it.proportion ?: 0.0
        }

        return Measurement(
            lighthouseResult?.requestedUrl,
            resultType,
            lighthouseResult?.userAgent,
            Strategy.DESKTOP,
            analysisUTCTimestamp?.toInstant(),
            loadingTime
        )
    }*/

}
