package pl.pwr.maw.model

import java.time.Instant

sealed class MeasurementDto(
    open val id: Long?,
    open val url: String,
    open val resultType: ResultType,
    open val strategy: Strategy?,
    open val userAgent: String?,
    open val analysisTime: Instant,
    val type: ApiType
)

data class WebPageTestMeasurementDto(
    override val id: Long?,
    override val url: String,
    override val resultType: ResultType,
    override val strategy: Strategy?,
    override val userAgent: String?,
    override val analysisTime: Instant
) : MeasurementDto(id, url, resultType, strategy, userAgent, analysisTime, ApiType.WEB_PAGE_TEST)

data class PageSpeedMeasurementDto(
    override val id: Long?,
    override val url: String,
    override val resultType: ResultType,
    override val strategy: Strategy?,
    override val userAgent: String?,
    override val analysisTime: Instant,
    var firstContentfulPaint: Int?,
    var firstMeaningfulPaint: Int?,
    var largestContentfulPaint: Int?,
    var maxPotentialFirstInputDelay: Int?,
    var speedIndex: Double?
) : MeasurementDto(id, url, resultType, strategy, userAgent, analysisTime, ApiType.PAGE_SPEED)
