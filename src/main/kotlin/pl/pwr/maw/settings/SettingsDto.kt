package pl.pwr.maw.settings

import pl.pwr.maw.api.Api
import java.time.ZoneId

data class SettingsDto(
    val id: Long?,
    val pageUrl: String,
    val apis: List<Long>,
    val strategy: Strategy,
    val cronExpression: String,
    val zoneId: ZoneId,
    val jsonPathExpression: String?
) {
    fun toEntity(apis: Set<Api>, id: Long? = null) = Settings(
        id,
        pageUrl,
        apis,
        strategy,
        cronExpression,
        zoneId,
        jsonPathExpression
    )
}
