package pl.pwr.maw.apikey

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api-key")
class ApiSettingsController(
    private val apiKeyService: ApiSettingsService
) {

    @PostMapping
    suspend fun updateApiKey(@RequestBody apiSettings: ApiSettings) {
        return apiKeyService.saveApiKey(apiSettings)
    }

}
