package pl.pwr.maw.model.webpagetest

import com.fasterxml.jackson.databind.node.ObjectNode
import java.time.Instant

data class WebPageTestResponseData(
    val id: String,
    val url: String,
    val summary: String,
    val testUrl: String,
    val location: String,
    val from: String,
    val connectivity: String,
    val bwDown: Int,
    val bwUp: Int,
    val latency: Int,
    val plr: String,
    val mobile: Boolean,
    val completed: Instant,
    val tester: String,
    val testRuns: Int,
    val fvonly: Boolean,
    val successfulFVRuns: Int,
    val successfulRVRuns: Int,
    val average: ObjectNode,
    val standardDeviation: ObjectNode,
    val median: ObjectNode,
    val runs: ObjectNode
)
