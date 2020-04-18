package pl.pwr.maw.measurement

import pl.pwr.maw.measurementsettings.Strategy
import java.time.Instant

data class MeasurementDto(
    val url: String,
    val resultType: ResultType,
    val strategy: Strategy,
    val userAgent: String?,
    val analysisTime: Instant,
    val loadingTime: Double
) {
    fun toEntity(id: Long? = null) = Measurement(id, url, resultType, strategy, userAgent, analysisTime, loadingTime)
}
