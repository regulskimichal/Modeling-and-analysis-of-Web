package pl.pwr.maw.measurement

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import pl.pwr.maw.measurementsettings.Strategy
import java.time.Instant

@Table("measurements")
data class Measurement(
    @Id val id: Long?,
    val url: String,
    val resultType: ResultType,
    val strategy: Strategy,
    val userAgent: String?,
    val analysisTime: Instant,
    val loadingTime: Double
) {
    fun toDto() = MeasurementDto(url, resultType, strategy, userAgent, analysisTime, loadingTime)
}
