package pl.pwr.maw.model.pagespeed

data class RuntimeError(
    val code: Int,
    val message: String,
    val errors: List<Any>?
)
