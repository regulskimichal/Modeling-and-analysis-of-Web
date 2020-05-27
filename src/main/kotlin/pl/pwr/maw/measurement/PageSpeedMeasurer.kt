package pl.pwr.maw.measurement

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate
import pl.pwr.maw.api.ApiService

@Service
@Transactional(readOnly = true)
class PageSpeedMeasurer(
    @Value("\${api.pageSpeedInsightsUrl}") private val pageSpeedInsightsUrl: String,
    private val restTemplate: RestTemplate,
    private val measurementRepository: MeasurementRepository,
    private val apiService: ApiService
) : PerformanceMeasurer {

    override fun preformMeasurement(url: String, runs: Int, firstViewOnly: Boolean): MeasurementResult? =
        TODO("Not yet implemented")

    /*fun getResults(url: String): Measurement? {
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
