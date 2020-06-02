package pl.pwr.maw.setting

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.pwr.maw.model.Setting

@Repository
interface SettingRepository : JpaRepository<Setting, Long>
