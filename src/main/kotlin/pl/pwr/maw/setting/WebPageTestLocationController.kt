package pl.pwr.maw.setting

import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/settings/locations")
class WebPageTestLocationController(
    private val webPageTestLocationService: WebPageTestLocationService
) {

    @GetMapping
    fun getAllLocations(@RequestParam("api-key", required = false) apiKey: String?) = runBlocking {
        webPageTestLocationService.fetchLocations(apiKey)
    }

}
