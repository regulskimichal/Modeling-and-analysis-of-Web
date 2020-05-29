package pl.pwr.maw.settings

import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.support.CronSequenceGenerator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.pwr.maw.api.ApiKeyService
import pl.pwr.maw.commons.EntityNotFoundException
import pl.pwr.maw.commons.events.DeregisterEvent
import pl.pwr.maw.commons.events.RegisterEvent

@Service
@Transactional(readOnly = true)
class SettingService(
    private val apiKeyService: ApiKeyService,
    private val settingRepository: SettingRepository,
    private val eventPublisher: ApplicationEventPublisher
) {

    fun getSetting(id: Long): Setting {
        return settingRepository.findById(id)
            .orElseThrow { throw EntityNotFoundException<Setting>(id) }
    }

    fun getAllSettings(): List<Setting> {
        return settingRepository.findAll()
    }

    @Transactional
    fun saveSetting(settingDto: SettingDto): Setting {
        val apis = apiKeyService.getApiKey(settingDto.api)
        return save(settingDto.toEntity(apis))
    }

    @Transactional
    fun updateSetting(id: Long, settingDto: SettingDto): Setting {
        if (settingRepository.existsById(id)) {
            val apis = apiKeyService.getApiKey(settingDto.api)
            return save(settingDto.toEntity(apis, id))
        } else {
            throw EntityNotFoundException<Setting>(id)
        }
    }

    @Transactional
    fun deleteSetting(id: Long) {
        if (settingRepository.existsById(id)) {
            settingRepository.deleteById(id)
            eventPublisher.publishEvent(DeregisterEvent(this, id))
        } else {
            throw EntityNotFoundException<Setting>(id)
        }
    }

    @Transactional
    fun deleteAllSettings() {
        settingRepository.deleteAll()
    }

    private fun save(setting: Setting): Setting {
        if (CronSequenceGenerator.isValidExpression(setting.cronExpression)) {
            val saved = settingRepository.save(setting)
            eventPublisher.publishEvent(RegisterEvent(this, saved))
            return saved
        } else {
            throw IllegalArgumentException("Invalid value of cronExpression")
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(SettingService::class.java)
    }

}
