package pl.pwr.maw.setting

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.future.asCompletableFuture
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.util.UriComponentsBuilder
import pl.pwr.maw.model.webpagetest.Configuration
import pl.pwr.maw.model.webpagetest.ConfigurationElement
import pl.pwr.maw.model.webpagetest.WebPageTestLocationResponse

@Service
class WebPageTestLocationService(
    @Value("\${api.webPageTestLocationsUrl}") private val webPageTestLocationsUrl: String,
    private val webClient: WebClient
) {

    suspend fun fetchLocations(apiKey: String?) = withContext(IO) {
        async {
            val webPageTestLocationResponse = webClient.get()
                .uri(buildUrl(apiKey))
                .retrieve()
                .awaitBody<WebPageTestLocationResponse>()

            val elements = webPageTestLocationResponse.data
                ?.asSequence()
                ?.flatMap { (location, data) ->
                    data.browsers
                        .splitToSequence(",")
                        .map { browser -> ConfigurationElement(location, browser) }
                }
                ?.toList() ?: emptyList()

            Configuration(elements)
        }.asCompletableFuture()
    }

    private fun buildUrl(apiKey: String?) = UriComponentsBuilder.fromHttpUrl(webPageTestLocationsUrl)
        .queryParam("f", "json")
        .apply {
            apiKey?.let {
                queryParam("k", it)
            }
        }.build().toUri()

}
