package pl.pwr.maw.model.pagespeed

data class LighthouseResultV5(
    val audits: Map<String, LighthouseAuditResultV5>?,
    val categories: Categories?,
    val categoryGroups: Map<String, CategoryGroupV5>?,
    val configSettings: ConfigSettings?,
    val environment: Environment?,
    val fetchTime: String?,
    val finalUrl: String?,
    val i18n: I18n?,
    val lighthouseVersion: String?,
    val requestedUrl: String?,
    val runWarnings: List<Any>?,
    val runtimeError: RuntimeError?,
    val stackPacks: List<StackPack>?,
    val timing: Timing?,
    val userAgent: String?
)
