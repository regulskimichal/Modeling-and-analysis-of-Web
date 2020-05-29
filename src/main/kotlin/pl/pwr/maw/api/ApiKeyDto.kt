package pl.pwr.maw.api

data class ApiKeyDto(
    val name: String,
    val apiKey: String,
    val defaultKey: Boolean = false,
    val type: ApiKeyType
) {
    fun toEntity(id: Long? = null): ApiKey = when (type) {
        ApiKeyType.WEB_PAGE_TEST -> WebPageTestApiKey(id, name, apiKey, defaultKey)
        ApiKeyType.PAGE_SPEED -> PageSpeedApiKey(id, name, apiKey, defaultKey)
    }
}
