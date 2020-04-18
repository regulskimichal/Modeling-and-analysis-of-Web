package pl.pwr.maw.measurement

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import org.springframework.web.util.UriComponentsBuilder
import pl.pwr.maw.apikey.ApiSettingsService
import pl.pwr.maw.measurement.MeasurementRepository
import pl.pwr.maw.measurement.PerformanceMeasurer
import pl.pwr.maw.model.webpagetest.WebPageTestResponse
import pl.pwr.maw.model.webpagetest.WebPageTestInitResponse
import pl.pwr.maw.measurementsettings.Api
import reactor.core.Disposable

@Service
class WebPageTestMeasurer(
    @Value("\${webPageTestUrl}") private val webPageTestUrl: String,
    private val webClient: WebClient,
    private val measurementRepository: MeasurementRepository,
    private val apiSettingsService: ApiSettingsService
) : PerformanceMeasurer {

    suspend fun getResults(url: String, runs: Int = 1, firstViewOnly: Boolean): Disposable {
        val uri = UriComponentsBuilder.fromHttpUrl(webPageTestUrl)
            .queryParam("url", url)
            .queryParam("k", apiSettingsService.getApiKey(Api.WEB_PAGE_TEST))
            .queryParam("f", "json")
            .queryParam("runs", runs)
            .queryParam("fvonly", if (firstViewOnly) 1 else 0)
            .build().toUri()

        return webClient.get().uri(uri).exchange()
            .flatMap { it.bodyToMono<WebPageTestInitResponse>() }
            .flatMap { webClient.get().uri(it.data.jsonUrl).exchange() }
            .flatMap { it.bodyToMono<WebPageTestResponse>() }
            .repeat(10)
            .subscribe()
    }

}
