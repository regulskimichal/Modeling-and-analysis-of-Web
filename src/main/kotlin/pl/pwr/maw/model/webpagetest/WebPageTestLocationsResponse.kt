package pl.pwr.maw.model.webpagetest

data class WebPageTestLocationResponse(
    val statusCode: Int,
    val statusText: String,
    val data: Map<String, WebPageTestLocationData>?
)
