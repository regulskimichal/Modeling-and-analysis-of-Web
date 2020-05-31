package pl.pwr.maw.measurement

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import pl.pwr.maw.model.webpagetest.WebPageTestInitResponse
import pl.pwr.maw.model.webpagetest.WebPageTestResponse
import pl.pwr.maw.settings.WebPageTestSetting

@Service
class WebPageTestMeasurer(
    @Value("\${api.webPageTestUrl}") private val webPageTestUrl: String,
    private val restTemplate: RestTemplate
) : PerformanceMeasurer<WebPageTestSetting> {

    override fun preformMeasurement(setting: WebPageTestSetting): WebPageTestMeasurement {
        val uri = UriComponentsBuilder.fromHttpUrl(webPageTestUrl)
            .queryParam("url", setting.pageUrl)
            .queryParam("k", setting.apiKey)
            .queryParam("f", "json")
            //.queryParam("runs", TODO())
            //.queryParam("fvonly", TODO())
            .build().toUri()

        return restTemplate.getForObject(uri, WebPageTestInitResponse::class.java)?.let { webPageTestInitResponse ->
            restTemplate.getForObject(
                webPageTestInitResponse.data.jsonUrl,
                WebPageTestResponse::class.java
            )?.let { webPageTestResponse ->
                asMeasurement(webPageTestResponse)
            }
        }!!
    }

    private fun asMeasurement(response: WebPageTestResponse): WebPageTestMeasurement = TODO()

}
