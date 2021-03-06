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

    open var userAgent: String?,

    @Column(nullable = false)
    open var analysisTime: Instant

) {

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(name = "response_id")
    open lateinit var originalResponse: Response

    abstract fun setting(): Setting

    abstract fun toDto(): MeasurementDto

}

@Entity
@Table(name = "web_page_test_measurements")
data class WebPageTestMeasurement(
    override var id: Long? = null,
    override var resultType: ResultType,
    override var userAgent: String? = null,
    override var analysisTime: Instant = Instant.now(),
    var webPageTestId: String? = null,
    var loadTime: Int? = null,
    var ttfb: Int? = null,
    var domContentLoadedEventStart: Int? = null,
    var domContentLoadedEventEnd: Int? = null,
    var render: Int? = null,
    var visualComplete: Int? = null,
    var fullyLoaded: Int? = null,
    var requests: Int? = null,
    var firstPaint: Int? = null,
    var speedIndex: Int? = null,
    var domElements: Int? = null,
    var statusCode: Int,
    var version: String? = null
) : Measurement(id, resultType, userAgent, analysisTime) {

    @ManyToOne(optional = false)
    lateinit var setting: WebPageTestSetting

    override fun setting() = setting

    override fun toDto() = WebPageTestMeasurementDto(
        id,
        setting.pageUrl,
        resultType,
        userAgent,
        analysisTime,
        webPageTestId,
        loadTime,
        ttfb,
        domContentLoadedEventStart,
        domContentLoadedEventEnd,
        render,
        visualComplete,
        fullyLoaded,
        requests,
        firstPaint,
        speedIndex,
        domElements,
        statusCode,
        version
    )

}

@Entity
@Table(name = "page_speed_measurements")
data class PageSpeedMeasurement(
    override var id: Long? = null,
    override var resultType: ResultType,
    override var userAgent: String? = null,
    override var analysisTime: Instant = Instant.now(),
    var strategy: Strategy? = null,
    var firstContentfulPaint: Double? = null,
    var firstMeaningfulPaint: Double? = null,
    var largestContentfulPaint: Double? = null,
    var maxPotentialFirstInputDelay: Double? = null,
    var speedIndex: Double? = null
) : Measurement(id, resultType, userAgent, analysisTime) {

    @ManyToOne(optional = false)
    lateinit var setting: PageSpeedSetting

    override fun setting() = setting

    override fun toDto() = PageSpeedMeasurementDto(
        id,
        setting.pageUrl,
        resultType,
        userAgent,
        analysisTime,
        strategy,
        firstContentfulPaint,
        firstMeaningfulPaint,
        maxPotentialFirstInputDelay,
        largestContentfulPaint,
        speedIndex
    )

}
