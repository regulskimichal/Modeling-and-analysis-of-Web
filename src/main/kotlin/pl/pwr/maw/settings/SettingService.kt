package pl.pwr.maw.settings

import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.support.CronSequenceGenerator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.pwr.maw.api.ApiKeyService
import pl.pwr.maw.api.InvalidApiType
import pl.pwr.maw.api.PageSpeedApiKey
import pl.pwr.maw.api.WebPageTestApiKey
import pl.pwr.maw.commons.EntityNotFoundException
import pl.pwr.maw.commons.events.DeregisterEvent
import pl.pwr.maw.commons.events.RegisterEvent
import pl.pwr.maw.commons.logger

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
    fun saveSetting(setting: Setting, apiKeyId: Long): Setting {
        changeApiKey(setting, apiKeyId)
        return save(setting)
    }

    @Transactional
    fun updateSetting(id: Long, setting: Setting, apiKeyId: Long): Setting {
        return when (setting) {
            is WebPageTestSetting -> updateSetting(id, setting, apiKeyId)
            is PageSpeedSetting -> updateSetting(id, setting, apiKeyId)
        }
    }

    private fun updateSetting(id: Long, setting: WebPageTestSetting, apiKeyId: Long): WebPageTestSetting {
        val persistedSetting = getSetting(id)
        if (persistedSetting is WebPageTestSetting) {
            persistedSetting.also {
                if (persistedSetting.apiKey().id != apiKeyId) {
                    changeApiKey(it, apiKeyId)
                }
                it.pageUrl = setting.pageUrl
                it.cronExpression = setting.cronExpression
                it.zoneId = setting.zoneId
            }
            return save(persistedSetting)
        } else {
            throw InvalidApiType()
        }
    }

    private fun updateSetting(id: Long, setting: PageSpeedSetting, apiKeyId: Long): PageSpeedSetting {
        return TODO()
    }

    fun changeApiKey(setting: Setting, apiKeyId: Long) {
        val apiKey = apiKeyService.getApiKey(apiKeyId)
        when (setting) {
            is WebPageTestSetting -> setting.apiKey =
                if (apiKey is WebPageTestApiKey) apiKey else throw InvalidApiType()
            is PageSpeedSetting -> setting.apiKey = if (apiKey is PageSpeedApiKey) apiKey else throw InvalidApiType()
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

    private fun <T : Setting> save(setting: T): T {
        if (CronSequenceGenerator.isValidExpression(setting.cronExpression)) {
            val saved = settingRepository.save(setting)
            eventPublisher.publishEvent(RegisterEvent(this, saved))
            return saved
        } else {
            throw IllegalArgumentException("Invalid value of cronExpression")
        }
    }

    companion object {
        private val log by logger<ScheduleService>()
    }

}
