package pl.pwr.maw.measurement

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.util.UriComponentsBuilder
import pl.pwr.maw.model.WebPageTestMeasurement
import pl.pwr.maw.model.WebPageTestSetting
import pl.pwr.maw.model.webpagetest.WebPageTestInitResponse
import pl.pwr.maw.model.webpagetest.WebPageTestResponse
import java.net.URI

@Service
class WebPageTestMeasurer(
    @Value("\${api.webPageTestUrl}") private val webPageTestUrl: String,
    private val webClient: WebClient,
    private val objectMapper: ObjectMapper
) : PerformanceMeasurer<WebPageTestSetting> {

    override suspend fun preformMeasurement(setting: WebPageTestSetting): WebPageTestMeasurement? {
        val url = buildUrl(setting)

        val awaitSingle = webClient.get()
            .uri(url)
            .exchange()
            .awaitSingle()
            .awaitBody<WebPageTestInitResponse>()

        val response = webClient.get()
            .uri(awaitSingle.data.jsonUrl)
            .exchange()
            .awaitSingle()
            .awaitBody<WebPageTestResponse>()

        return response.asMeasurement()
    }

    private fun buildUrl(setting: WebPageTestSetting): URI {
        return UriComponentsBuilder.fromHttpUrl(webPageTestUrl)
            .queryParam("url", setting.pageUrl)
            .queryParam("k", setting.apiKey)
            .queryParam("f", "json")
            //.queryParam("runs", TODO())
            //.queryParam("fvonly", TODO())
            .build().toUri()
    }

    private fun WebPageTestResponse.asMeasurement(): WebPageTestMeasurement = TODO()

}
