package pl.pwr.maw.model

import javax.persistence.Embeddable

@Embeddable
data class Configuration(
    var location: String,
    var browser: String,
    var connectivityProfile: ConnectivityProfile
)
