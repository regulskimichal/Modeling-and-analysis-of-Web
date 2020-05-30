package pl.pwr.maw.settings

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import pl.pwr.maw.api.ApiKey
import pl.pwr.maw.api.ApiKeyType
import java.time.ZoneId

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(WebPageTestSettingDto::class, name = ApiKeyType.WEB_PAGE_TEST_NAME),
    JsonSubTypes.Type(PageSpeedSettingDto::class, name = ApiKeyType.PAGE_SPEED_NAME)
)
sealed class SettingDto(
    open val pageUrl: String,
    open val apiKeyId: Long,
    open val cronExpression: String,
    open val zoneId: ZoneId
) {
    abstract fun toEntity(apiKey: ApiKey, id: Long? = null): Setting
}

data class WebPageTestSettingDto(
    override val pageUrl: String,
    override val apiKeyId: Long,
    override val cronExpression: String,
    override val zoneId: ZoneId
) : SettingDto(pageUrl, apiKeyId, cronExpression, zoneId) {
    override fun toEntity(apiKey: ApiKey, id: Long?): Setting =
        WebPageTestSetting(id, pageUrl, apiKey, cronExpression, zoneId)
}

data class PageSpeedSettingDto(
    override val pageUrl: String,
    override val apiKeyId: Long,
    override val cronExpression: String,
    override val zoneId: ZoneId
) : SettingDto(pageUrl, apiKeyId, cronExpression, zoneId) {
    override fun toEntity(apiKey: ApiKey, id: Long?) = PageSpeedSetting(id, pageUrl, apiKey, cronExpression, zoneId)
}
