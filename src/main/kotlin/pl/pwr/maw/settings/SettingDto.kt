package pl.pwr.maw.settings

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
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
    abstract fun toEntity(): Setting
}

data class WebPageTestSettingDto(
    override val pageUrl: String,
    override val apiKeyId: Long,
    override val cronExpression: String,
    override val zoneId: ZoneId
) : SettingDto(pageUrl, apiKeyId, cronExpression, zoneId) {
    override fun toEntity(): Setting = WebPageTestSetting(pageUrl, cronExpression, zoneId)
}

data class PageSpeedSettingDto(
    override val pageUrl: String,
    override val apiKeyId: Long,
    override val cronExpression: String,
    override val zoneId: ZoneId,
    val strategy: Strategy?
) : SettingDto(pageUrl, apiKeyId, cronExpression, zoneId) {
    override fun toEntity() = PageSpeedSetting(pageUrl, cronExpression, zoneId, strategy)
}
