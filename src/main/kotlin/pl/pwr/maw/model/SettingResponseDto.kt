package pl.pwr.maw.model

import java.time.ZoneId

sealed class SettingResponseDto(
    open val id: Long? = null,
    open val pageUrl: String,
    open val apiKeyId: Long?,
    open val cronExpression: String,
    open val zoneId: ZoneId,
    val type: ApiType
)

data class WebPageTestSettingResponseDto(
    override val id: Long? = null,
    override val pageUrl: String,
    override val apiKeyId: Long?,
    override val cronExpression: String,
    override val zoneId: ZoneId,
    val enabled: Boolean,
    val configuration: Configuration?
) : SettingResponseDto(id, pageUrl, apiKeyId, cronExpression, zoneId, ApiType.WEB_PAGE_TEST)

data class PageSpeedSettingResponseDto(
    override val id: Long? = null,
    override val pageUrl: String,
    override val apiKeyId: Long?,
    override val cronExpression: String,
    override val zoneId: ZoneId,
    val enabled: Boolean,
    val strategy: Strategy?
) : SettingResponseDto(id, pageUrl, apiKeyId, cronExpression, zoneId, ApiType.PAGE_SPEED)
