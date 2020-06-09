package pl.pwr.maw.model.webpagetest

import com.fasterxml.jackson.databind.node.ObjectNode

data class WebPageTestResponse(
    val data: ObjectNode?,
    val statusCode: Int,
    val statusText: String,
    val webPagetestVersion: String?
)
