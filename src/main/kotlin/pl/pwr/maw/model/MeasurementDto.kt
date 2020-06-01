package pl.pwr.maw.model

import java.time.Instant

sealed class MeasurementDto(
    open val id: Long?,
    open val url: String,
    open val resultType: ResultType,
    open val strategy: Strategy?,
    open val userAgent: String?,
    open val analysisTime: Instant
)

data class WebPageTestMeasurementDto(
    override val id: Long?,
    override val url: String,
    override val resultType: ResultType,
    override val strategy: Strategy?,
    override val userAgent: String?,
    override val analysisTime: Instant
) : MeasurementDto(id, url, resultType, strategy, userAgent, analysisTime)

data class PageSpeedMeasurementDto(
    override val id: Long?,
    override val url: String,
    override val resultType: ResultType,
    override val strategy: Strategy?,
    override val userAgent: String?,
    override val analysisTime: Instant,
    val largestContentfulPaint: Double?,
    val firstMeaningfulPaint: Double?
) : MeasurementDto(id, url, resultType, strategy, userAgent, analysisTime)
