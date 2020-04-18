package pl.pwr.maw.model.pagespeed

import com.fasterxml.jackson.annotation.JsonProperty

data class PagespeedApiLoadingExperienceV5(
        val id: String?,
        @JsonProperty("initial_url") val initialUrl: String?,
        val metrics: Map<String, UserPageLoadMetricV5>?,
        @JsonProperty("overall_category") val overallCategory: String?
)
