package pl.pwr.maw.measurement

import pl.pwr.maw.model.webpagetest.WebPageTestResponse

class WebPageTestApiException(
    val json: String,
    val response: WebPageTestResponse
) : RuntimeException()
