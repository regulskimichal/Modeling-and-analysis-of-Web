package pl.pwr.maw.api

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api-key")
@Tag(name = "API keys")
class ApiKeyController(
    private val apiKeyService: ApiKeyService
) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAllApiKeys(@RequestParam("type", required = false) apiTypeName: String?): List<ApiKey> {
        return apiKeyService.getAllApiKeys(apiTypeName)
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getApiKey(@PathVariable id: Long): ApiKey {
        return apiKeyService.getApiKey(id)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createApiKey(@RequestBody apiKey: ApiKeyDto): ApiKey {
        return apiKeyService.saveApiKey(apiKey.toEntity())
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateApiKey(@PathVariable id: Long, @RequestBody apiKey: ApiKeyDto) {
        apiKeyService.updateApiKey(apiKey.toEntity(id))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteApiKey(@PathVariable id: Long) {
        apiKeyService.deleteApiKey(id)
    }

}
