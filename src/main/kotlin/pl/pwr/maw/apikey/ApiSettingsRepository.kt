package pl.pwr.maw.apikey

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ApiSettingsRepository : ReactiveCrudRepository<ApiSettings, String>
