package pl.pwr.maw.model.pagespeed

data class UserPageLoadMetricV5(
        val category: String?,
        val distributions: List<Bucket>?,
        val formFactor: String?,
        val median: Int?,
        val metricId: String?,
        val percentile: Int?
)
