package pl.pwr.maw.model.webpagetest

data class WebPageTestResponse(
    val data: WebPageTestResponseData,
    val statusCode: Int,
    val statusText: String,
    val webPagetestVersion: String?
)
