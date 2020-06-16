package pl.pwr.maw.model.webpagetest

data class WebPageTestInitResponse(
    val statusCode: Int,
    val statusText: String,
    val data: WebPageTestInitResponseData?
)
