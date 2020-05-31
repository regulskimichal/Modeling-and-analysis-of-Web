package pl.pwr.maw.settings

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.pwr.maw.model.Setting

@Repository
interface SettingRepository : JpaRepository<Setting, Long>
