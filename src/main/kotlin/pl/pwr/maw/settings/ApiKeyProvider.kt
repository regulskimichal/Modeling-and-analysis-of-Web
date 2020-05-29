package pl.pwr.maw.settings

import pl.pwr.maw.api.ApiKey

interface ApiKeyProvider {
    val apiKey: ApiKey
}
