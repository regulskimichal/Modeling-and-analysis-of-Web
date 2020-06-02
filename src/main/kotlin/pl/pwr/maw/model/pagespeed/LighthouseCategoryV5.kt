package pl.pwr.maw.model.pagespeed

data class LighthouseCategoryV5(
    val auditRefs: List<AuditRefs>,
    val description: String?,
    val id: String,
    val manualDescription: String?,
    val score: Any?,
    val title: String
)
