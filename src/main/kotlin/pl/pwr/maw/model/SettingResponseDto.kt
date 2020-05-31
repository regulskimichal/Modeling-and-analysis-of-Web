package pl.pwr.maw.model

import java.time.ZoneId

sealed class SettingResponseDto(
    open val id: Long? = null,
    open val pageUrl: String,
    open val apiKeyId: Long?,
    open val cronExpression: String,
    open val zoneId: ZoneId
)

data class WebPageTestSettingResponseDto(
    override val id: Long? = null,
    override val pageUrl: String,
    override val apiKeyId: Long?,
    override val cronExpression: String,
    override val zoneId: ZoneId
) : SettingResponseDto(id, pageUrl, apiKeyId, cronExpression, zoneId)

data class PageSpeedSettingResponseDto(
    override val id: Long? = null,
    override val pageUrl: String,
    override val apiKeyId: Long?,
    override val cronExpression: String,
    override val zoneId: ZoneId
) : SettingResponseDto(id, pageUrl, apiKeyId, cronExpression, zoneId)
