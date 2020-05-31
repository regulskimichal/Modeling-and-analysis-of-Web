package pl.pwr.maw.settings

import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.support.CronSequenceGenerator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.pwr.maw.api.ApiKeyService
import pl.pwr.maw.api.InvalidApiType
import pl.pwr.maw.commons.EntityNotFoundException
import pl.pwr.maw.events.DeregisterEvent
import pl.pwr.maw.events.RegisterEvent
import pl.pwr.maw.model.*

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
    fun <T : Setting> saveSetting(setting: T, apiKeyId: Long): T {
        changeApiKey(setting, apiKeyId)
        return save(setting)
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

    private fun <T : Setting> changeApiKey(setting: T, apiKeyId: Long) {
        val apiKey = apiKeyService.getApiKey(apiKeyId)
        when (setting) {
            is WebPageTestSetting -> setting.apiKey =
                if (apiKey is WebPageTestApiKey) apiKey else throw InvalidApiType()
            is PageSpeedSetting -> setting.apiKey = if (apiKey is PageSpeedApiKey) apiKey else throw InvalidApiType()
        }
    }

    private fun <T : Setting> save(setting: T): T {
        if (CronSequenceGenerator.isValidExpression(setting.cronExpression)) {
            val saved = settingRepository.save(setting)
            eventPublisher.publishEvent(RegisterEvent(this, saved))
            return saved
        } else {
            throw IllegalArgumentException("Invalid value of cronExpression")
        }
    }

}
