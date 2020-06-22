package pl.pwr.maw.model

import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
data class BrowserConfiguration(
    var location: String,
    var browser: String,
    @Enumerated(EnumType.STRING)
    var connectivityProfile: ConnectivityProfile = ConnectivityProfile.NATIVE
)
