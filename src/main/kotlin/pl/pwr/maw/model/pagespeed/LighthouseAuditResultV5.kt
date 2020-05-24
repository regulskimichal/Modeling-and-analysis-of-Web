package pl.pwr.maw.model.pagespeed

data class LighthouseAuditResultV5(
    val description: String?,
    val details: Map<String, Any>?,
    val displayValue: String?,
    val errorMessage: String?,
    val explanation: String?,
    val id: String?,
    val numericValue: Double?,
    val score: Any?,
    val scoreDisplayMode: String?,
    val title: String?,
    val warnings: Any?
)
