package pl.pwr.maw.model.webpagetest

data class WebPageTestResponseData(
    val statusCode: Int,
    val statusText: String,
    val id: String,
    val testInfo: Map<String, Any>,
    val testId: String,
    val runs: Int,
    val fvonly: Int,
    val remote: Boolean,
    val testsExpected: Int,
    val location: String,
    val startTime: String,
    val elapsed: Int,
    val fvRunsCompleted: Int,
    val rvRunsCompleted: Int,
    val testsCompleted: Int
)
