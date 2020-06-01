package pl.pwr.maw.model

import org.hibernate.annotations.LazyToOne
import org.hibernate.annotations.LazyToOneOption
import java.time.Instant
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
sealed class Measurement(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "measurements_seq_gen")
    @SequenceGenerator(name = "measurements_seq_gen", sequenceName = "measurements_id_seq")
    @Column(unique = true, updatable = false, nullable = false)
    open var id: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    open var resultType: ResultType,

    @Enumerated(EnumType.STRING)
    open var strategy: Strategy?,

    open var userAgent: String?,

    @Column(nullable = false)
    open var analysisTime: Instant

) {

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(name = "response_id")
    lateinit var originalResponse: Response

    abstract fun setting(): Setting

    abstract fun toDto(): MeasurementDto

}

@Entity
@Table(name = "web_page_test_measurements")
data class WebPageTestMeasurement(
    override var id: Long? = null,
    override var resultType: ResultType,
    override var strategy: Strategy?,
    override var userAgent: String?,
    override var analysisTime: Instant
) : Measurement(id, resultType, strategy, userAgent, analysisTime) {

    @ManyToOne(optional = false)
    lateinit var setting: WebPageTestSetting

    override fun setting() = setting

    override fun toDto() = WebPageTestMeasurementDto(
        id,
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
    override var resultType: ResultType,
    override var strategy: Strategy?,
    override var userAgent: String?,
    override var analysisTime: Instant,
    var largestContentfulPaint: Double?,
    var firstMeaningfulPaint: Double?
) : Measurement(id, resultType, strategy, userAgent, analysisTime) {

    @ManyToOne(optional = false)
    lateinit var setting: PageSpeedSetting

    override fun setting() = setting

    override fun toDto() = PageSpeedMeasurementDto(
        id,
        setting.pageUrl,
        resultType,
        strategy,
        userAgent,
        analysisTime,
        largestContentfulPaint,
        firstMeaningfulPaint
    )

}
