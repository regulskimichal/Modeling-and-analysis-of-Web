package pl.pwr.maw.measurement

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import pl.pwr.maw.api.ApiKeyService
import pl.pwr.maw.model.webpagetest.WebPageTestInitResponse
import pl.pwr.maw.model.webpagetest.WebPageTestResponse

@Service
class WebPageTestMeasurer(
    @Value("\${api.webPageTestUrl}") private val webPageTestUrl: String,
    private val apiKeyService: ApiKeyService,
    private val restTemplate: RestTemplate
) : PerformanceMeasurer {

    override fun preformMeasurement(url: String, runs: Int, firstViewOnly: Boolean): MeasurementResult? {
        val uri = UriComponentsBuilder.fromHttpUrl(webPageTestUrl)
            .queryParam("url", url)
            //.queryParam("k", apiService.getApiKey(Api.WEB_PAGE_TEST_ID).apiKey)
            .queryParam("f", "json")
            .queryParam("runs", runs)
            .queryParam("fvonly", if (firstViewOnly) 1 else 0)
            .build().toUri()

        return restTemplate.getForObject(uri, WebPageTestInitResponse::class.java)?.let { webPageTestInitResponse ->
            restTemplate.getForObject(
                webPageTestInitResponse.data.jsonUrl,
                WebPageTestResponse::class.java
            )?.let { webPageTestResponse ->
                asMeasurement(webPageTestResponse)
            }
        }
    }

    private fun asMeasurement(response: WebPageTestResponse): MeasurementResult = TODO()

}
