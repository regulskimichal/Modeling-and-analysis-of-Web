package pl.pwr.maw.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(WebPageTestApiKeyDto::class, name = ApiType.WEB_PAGE_TEST_NAME),
    JsonSubTypes.Type(PageSpeedApiKeyDto::class, name = ApiType.PAGE_SPEED_NAME)
)
sealed class ApiKeyDto(
    open val name: String,
    open val apiKey: String,
    open val defaultKey: Boolean = false
) {
    abstract fun toEntity(id: Long? = null): ApiKey
}

data class WebPageTestApiKeyDto(
    override val name: String,
    override val apiKey: String,
    override val defaultKey: Boolean = false
) : ApiKeyDto(name, apiKey, defaultKey) {
    override fun toEntity(id: Long?): WebPageTestApiKey =
        WebPageTestApiKey(id, name, apiKey, defaultKey)
}

data class PageSpeedApiKeyDto(
    override val name: String,
    override val apiKey: String,
    override val defaultKey: Boolean = false
) : ApiKeyDto(name, apiKey, defaultKey) {
    override fun toEntity(id: Long?): ApiKey =
        PageSpeedApiKey(id, name, apiKey, defaultKey)
}
