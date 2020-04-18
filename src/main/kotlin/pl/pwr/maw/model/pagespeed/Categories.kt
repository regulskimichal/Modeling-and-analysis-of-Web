package pl.pwr.maw.model.pagespeed

import com.fasterxml.jackson.annotation.JsonProperty

data class Categories(
        val accessibility: LighthouseCategoryV5?,
        @JsonProperty("best-practices") val bestPractices: LighthouseCategoryV5?,
        val performance: LighthouseCategoryV5?,
        val pwa: LighthouseCategoryV5?,
        val seo: LighthouseCategoryV5?
)
