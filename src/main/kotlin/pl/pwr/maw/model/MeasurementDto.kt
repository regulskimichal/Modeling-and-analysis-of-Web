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
    val idxml: String? = null,
    val loadtime: String? = null,
    val ttfb: String? = null,
    val domstart: String? = null,
    val domend: String? = null,
    val render: String? = null,
    val visualComplete: String? = null,
    val fullyLoaded: String? = null,
    val requests: String? = null,
    val firstPaint: String? = null,
    val speedidx: String? = null,
    val domnumber: String? = null,
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
    val firstContentfulPaint: Int?,
    val firstMeaningfulPaint: Int?,
    val largestContentfulPaint: Int?,
    val maxPotentialFirstInputDelay: Int?,
    val speedIndex: Double?
) : MeasurementDto(id, url, resultType, userAgent, analysisTime, ApiType.PAGE_SPEED)
