package pl.pwr.maw.model

import javax.persistence.Embeddable

@Embeddable
data class BrowserConfiguration(
    var location: String,
    var browser: String,
    var connectivityProfile: ConnectivityProfile
)
