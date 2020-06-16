package pl.pwr.maw.measurement

import pl.pwr.maw.model.webpagetest.WebPageTestInitResponse

class WebPageTestInitApiException(
    val json: String,
    val response: WebPageTestInitResponse
) : RuntimeException()
