package pl.pwr.maw.model

import org.hibernate.annotations.Type
import java.time.Instant
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
sealed class Measurement(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(updatable = false, nullable = false)
    open var id: Long? = null,

    @Basic(fetch = FetchType.LAZY)
    @Type(type = "text")
    open var resultJson: String?,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    open var resultType: ResultType,

    @Enumerated(EnumType.STRING)
    open var strategy: Strategy?,

    open var userAgent: String?,

    @Column(nullable = false)
    open var analysisTime: Instant

) {

    abstract fun setting(): Setting

    abstract fun toDto(): MeasurementDto

}

@Entity
@Table(name = "web_page_test_measurements")
data class WebPageTestMeasurement(
    override var id: Long? = null,
    override var resultJson: String?,
    override var resultType: ResultType,
    override var strategy: Strategy?,
    override var userAgent: String?,
    override var analysisTime: Instant
) : Measurement(id, resultJson, resultType, strategy, userAgent, analysisTime) {

    @ManyToOne(optional = false)
    lateinit var setting: WebPageTestSetting

    override fun setting() = setting

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
    override var id: Long? = null,
    override var resultJson: String?,
    override var resultType: ResultType,
    override var strategy: Strategy?,
    override var userAgent: String?,
    override var analysisTime: Instant,
    var largestContentfulPaint: Double?,
    var firstMeaningfulPaint: Double?
) : Measurement(id, resultJson, resultType, strategy, userAgent, analysisTime) {

    @ManyToOne(optional = false)
    lateinit var setting: PageSpeedSetting

    override fun setting() = setting

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
