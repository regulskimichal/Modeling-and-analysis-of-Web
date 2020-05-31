package pl.pwr.maw.model

import java.time.Instant
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
sealed class Measurement(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    open var id: Long?,

    @ManyToOne
    open var setting: Setting,

    @Basic(fetch = FetchType.LAZY)
    open var resultJson: String?,

    @Enumerated(EnumType.STRING)
    open var resultType: ResultType,

    @Enumerated(EnumType.STRING)
    open var strategy: Strategy?,

    open var userAgent: String?,

    open var analysisTime: Instant
) {
    abstract fun toDto(): MeasurementDto
}

@Entity
@Table(name = "web_page_test_measurements")
data class WebPageTestMeasurement(
    override var id: Long?,

    @ManyToOne
    override var setting: Setting,

    @Basic(fetch = FetchType.LAZY)
    override var resultJson: String?,

    @Enumerated(EnumType.STRING)
    override var resultType: ResultType,

    @Enumerated(EnumType.STRING)
    override var strategy: Strategy?,

    override var userAgent: String?,

    override var analysisTime: Instant
) : Measurement(id, setting, resultJson, resultType, strategy, userAgent, analysisTime) {
    override fun toDto() = WebPageTestMeasurementDto(
        setting.pageUrl,
        resultType,
        strategy,
        userAgent,
        analysisTime
    )
}

@Entity
@Table(name = "page_speed_measurements")
data class PageSpeedMeasurement(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    override var id: Long?,

    @ManyToOne
    override var setting: Setting,

    @Basic(fetch = FetchType.LAZY)
    override var resultJson: String?,

    @Enumerated(EnumType.STRING)
    override var resultType: ResultType,

    @Enumerated(EnumType.STRING)
    override var strategy: Strategy?,

    override var userAgent: String?,

    override var analysisTime: Instant,

    var largestContentfulPaint: Long?,

    var firstMeaningfulPaint: Long?
) : Measurement(id, setting, resultJson, resultType, strategy, userAgent, analysisTime) {
    override fun toDto() = PageSpeedMeasurementDto(
        setting.pageUrl,
        resultType,
        strategy,
        userAgent,
        analysisTime,
        largestContentfulPaint,
        firstMeaningfulPaint
    )
}
