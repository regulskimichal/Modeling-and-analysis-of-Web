package pl.pwr.maw.settings

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SettingsRepository : JpaRepository<Settings, Long>