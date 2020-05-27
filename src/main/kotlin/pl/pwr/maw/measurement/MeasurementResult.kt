package pl.pwr.maw.measurement

import pl.pwr.maw.settings.Strategy
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "measurements")
data class MeasurementResult(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long?,

    var url: String,

    @Enumerated(EnumType.STRING)
    var resultType: ResultType,

    @Enumerated(EnumType.STRING)
    var strategy: Strategy,

    var userAgent: String?,

    var analysisTime: Instant,

    var loadingTime: Double

) {
    fun toDto() = MeasurementDto(url, resultType, strategy, userAgent, analysisTime, loadingTime)
}
