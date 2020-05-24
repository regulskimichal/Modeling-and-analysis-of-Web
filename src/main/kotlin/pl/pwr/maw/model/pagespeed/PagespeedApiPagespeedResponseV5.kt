package pl.pwr.maw.model.pagespeed

import java.time.OffsetDateTime

data class PagespeedApiPagespeedResponseV5(
    val analysisUTCTimestamp: OffsetDateTime?,
    val captchaResult: String?,
    val id: String?,
    val kind: String?,
    val lighthouseResult: LighthouseResultV5?,
    val loadingExperience: PagespeedApiLoadingExperienceV5?,
    val originLoadingExperience: PagespeedApiLoadingExperienceV5?,
    val version: PagespeedVersion?
)
