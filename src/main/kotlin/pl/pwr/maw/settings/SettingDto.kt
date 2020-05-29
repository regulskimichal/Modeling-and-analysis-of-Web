package pl.pwr.maw.settings

import pl.pwr.maw.api.ApiKey
import java.time.ZoneId

data class SettingDto(
    val id: Long?,
    val pageUrl: String,
    val api: Long,
    val cronExpression: String,
    val zoneId: ZoneId
) {
    fun toEntity(apiKey: ApiKey, id: Long? = null): Setting = TODO() /* Setting(
        id,
        pageUrl,
        apiKey,
        cronExpression,
        zoneId
    )*/
}
