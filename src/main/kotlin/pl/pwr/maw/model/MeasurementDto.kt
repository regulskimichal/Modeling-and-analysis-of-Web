package pl.pwr.maw.model

import java.time.Instant

sealed class MeasurementDto(
    open val id: Long?,
    open val url: String,
    open val resultType: ResultType,
    open val userAgent: String?,
    open val analysisTime: Instant,
    val type: ApiType
)

data class WebPageTestMeasurementDto(
    override val id: Long?,
    override val url: String,
    override val resultType: ResultType,
    override val userAgent: String?,
    override val analysisTime: Instant,
    val webPageTestId: String? = null,
    val loadTime: Int? = null,
    val ttfb: Int? = null,
    val domContentLoadedEventStart: Int? = null,
    val domContentLoadedEventEnd: Int? = null,
    val render: Int? = null,
    val visualComplete: Int? = null,
    val fullyLoaded: Int? = null,
    val requests: Int? = null,
    val firstPaint: Int? = null,
    val speedIndex: Int? = null,
    val domElements: Int? = null,
    val statusCode: Int,
    val version: String?
) : MeasurementDto(id, url, resultType, userAgent, analysisTime, ApiType.WEB_PAGE_TEST)

data class PageSpeedMeasurementDto(
    override val id: Long?,
    override val url: String,
    override val resultType: ResultType,
    override val userAgent: String?,
    override val analysisTime: Instant,
    val strategy: Strategy?,
    val firstContentfulPaint: Double?,
    val firstMeaningfulPaint: Double?,
    val largestContentfulPaint: Double?,
    val maxPotentialFirstInputDelay: Double?,
    val speedIndex: Double?
) : MeasurementDto(id, url, resultType, userAgent, analysisTime, ApiType.PAGE_SPEED)
