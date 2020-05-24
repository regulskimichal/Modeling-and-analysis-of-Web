package pl.pwr.maw.model.webpagetest

data class WebPageTestInitResponseData(
    val testId: String,
    val ownerKey: String,
    val jsonUrl: String,
    val xmlUrl: String,
    val userUrl: String,
    val summaryCSV: String,
    val detailCSV: String
)
