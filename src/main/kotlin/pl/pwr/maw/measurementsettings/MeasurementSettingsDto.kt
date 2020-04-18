package pl.pwr.maw.measurementsettings

import java.time.ZoneId

data class MeasurementSettingsDto(
    val pageUrl: String,
    val cronExpression: String,
    val zoneId: ZoneId,
    val jsonPathExpression: String?
) {
    fun toEntity(id: Long? = null) = MeasurementSettings(id, pageUrl, cronExpression, zoneId, jsonPathExpression)
}
