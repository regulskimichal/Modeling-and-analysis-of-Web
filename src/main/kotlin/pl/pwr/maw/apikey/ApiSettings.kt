package pl.pwr.maw.apikey

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("api_settings")
data class ApiSettings(
    @Id val apiName: String,
    val apiKey: String
)
