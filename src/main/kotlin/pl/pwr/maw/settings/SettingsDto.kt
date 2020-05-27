package pl.pwr.maw.settings

import pl.pwr.maw.api.Api
import java.time.ZoneId

data class SettingsDto(
    val id: Long?,
    val pageUrl: String,
    val api: Long,
    val strategy: Strategy,
    val cronExpression: String,
    val zoneId: ZoneId,
    val jsonPathExpression: String?
) {
    fun toEntity(api: Api, id: Long? = null) = Settings(
        id,
        pageUrl,
        api,
        strategy,
        cronExpression,
        zoneId,
        jsonPathExpression
    )
}
