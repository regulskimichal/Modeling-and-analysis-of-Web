package pl.pwr.maw.measurementsettings

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.ZoneId

@Table("measurement_settings")
data class MeasurementSettings(
    @Id val id: Long? = null,
    val pageUrl: String,
    val cronExpression: String,
    val zoneId: ZoneId,
    val jsonPathExpression: String? = null
) {
    fun toDto() = MeasurementSettingsDto(pageUrl, cronExpression, zoneId, jsonPathExpression)
}
