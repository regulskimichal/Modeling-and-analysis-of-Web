package pl.pwr.maw.model.webpagetest

import com.fasterxml.jackson.annotation.JsonProperty

data class WebPageTestLocationData(
    @JsonProperty("Label") val label: String,
    val location: String,
    @JsonProperty("Browsers") val browsers: String,
    val status: String,
    val relayServer: String?,
    val relayLocation: String?,
    val labelShort: String,
    val default: Boolean?,
    val group: String,
    @JsonProperty("PendingTests") val pendingTests: Map<String, Any?>
)
