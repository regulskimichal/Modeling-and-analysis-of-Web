package pl.pwr.maw.measurement

import pl.pwr.maw.settings.Strategy
import java.time.Instant

sealed class MeasurementDto(
    open val url: String,
    open val resultType: ResultType,
    open val strategy: Strategy?,
    open val userAgent: String?,
    open val analysisTime: Instant
)

data class WebPageTestMeasurementDto(
    override val url: String,
    override val resultType: ResultType,
    override val strategy: Strategy?,
    override val userAgent: String?,
    override val analysisTime: Instant
) : MeasurementDto(url, resultType, strategy, userAgent, analysisTime)

data class PageSpeedMeasurementDto(
    override val url: String,
    override val resultType: ResultType,
    override val strategy: Strategy?,
    override val userAgent: String?,
    override val analysisTime: Instant,
    val largestContentfulPaint: Long?,
    val firstMeaningfulPaint: Long?
) : MeasurementDto(url, resultType, strategy, userAgent, analysisTime)
