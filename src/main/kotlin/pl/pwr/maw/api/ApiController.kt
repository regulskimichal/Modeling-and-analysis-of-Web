package pl.pwr.maw.api

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
@Tag(name = "APIs")
class ApiController(
    private val apiKeyService: ApiService
) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAvailableApis(): List<Api> {
        return apiKeyService.getApis()
    }

    @PostMapping("/{id}/key")
    @ResponseStatus(HttpStatus.CREATED)
    fun updateApiKeys(@PathVariable id: Long, @RequestBody apiKey: ApiKeyDto): Api {
        return apiKeyService.saveApiKey(id, apiKey)
    }

}
