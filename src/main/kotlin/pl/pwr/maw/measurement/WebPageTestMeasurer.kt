package pl.pwr.maw.measurement

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import pl.pwr.maw.api.ApiService
import pl.pwr.maw.model.webpagetest.WebPageTestResponse

@Service
class WebPageTestMeasurer(
    @Value("\${api.webPageTestUrl}") private val webPageTestUrl: String,
    private val apiService: ApiService,
    private val restTemplate: RestTemplate
) : PerformanceMeasurer {

    override fun preformMeasurement(url: String, runs: Int, firstViewOnly: Boolean): MeasurementResult? {
        TODO()

        /*val uri = UriComponentsBuilder.fromHttpUrl(webPageTestUrl)
            .queryParam("url", url)
            .queryParam("k", apiService.getApiKey(Api.WEB_PAGE_TEST))
            .queryParam("f", "json")
            .queryParam("runs", runs)
            .queryParam("fvonly", if (firstViewOnly) 1 else 0)
            .build().toUri()

        return restTemplate.uri(uri).exchange()
            .flatMap { it.bodyToMono<WebPageTestInitResponse>() }
            .flatMap { restTemplate.get().uri(it.data.jsonUrl).exchange() }
            .flatMap { it.bodyToMono<WebPageTestResponse>() }
            .repeat(10)
            .map { asMeasurement(it) }
            .awaitFirstOrNull()*/
    }

    private fun asMeasurement(response: WebPageTestResponse): MeasurementResult = TODO()

}
