package pl.pwr.maw.measurement

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import org.springframework.web.util.UriComponentsBuilder
import pl.pwr.maw.model.WebPageTestMeasurement
import pl.pwr.maw.model.WebPageTestSetting
import pl.pwr.maw.model.webpagetest.WebPageTestInitResponse
import pl.pwr.maw.model.webpagetest.WebPageTestResponse

@Service
class WebPageTestMeasurer(
    @Value("\${api.webPageTestUrl}") private val webPageTestUrl: String,
    private val webClient: WebClient,
    private val objectMapper: ObjectMapper
) : PerformanceMeasurer<WebPageTestSetting> {

    override fun preformMeasurement(setting: WebPageTestSetting): WebPageTestMeasurement {
        val uri = UriComponentsBuilder.fromHttpUrl(webPageTestUrl)
            .queryParam("url", setting.pageUrl)
            .queryParam("k", setting.apiKey)
            .queryParam("f", "json")
            //.queryParam("runs", TODO())
            //.queryParam("fvonly", TODO())
            .build().toUri()

        return webClient.get().uri(uri).exchange()
            .flatMap { it.bodyToMono<WebPageTestInitResponse>() }
            .flatMap { webClient.get().uri(it.data.jsonUrl).exchange() }
            .flatMap { it.bodyToMono<WebPageTestResponse>() }
            .map { it.asMeasurement() }
            .block()!!
    }

    private fun WebPageTestResponse.asMeasurement(): WebPageTestMeasurement = TODO()

}
