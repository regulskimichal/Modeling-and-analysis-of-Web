package pl.pwr.maw.setting

import com.fasterxml.jackson.dataformat.csv.CsvMapper
import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.support.CronSequenceGenerator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.pwr.maw.api.ApiKeyService
import pl.pwr.maw.api.InvalidApiTypeException
import pl.pwr.maw.commons.EntityNotFoundException
import pl.pwr.maw.events.DeregisterEvent
import pl.pwr.maw.events.RegisterEvent
import pl.pwr.maw.model.*

@Service
@Transactional(readOnly = true)
class SettingService(
    private val apiKeyService: ApiKeyService,
    private val settingRepository: SettingRepository,
    private val eventPublisher: ApplicationEventPublisher,
    private val csvMapper: CsvMapper
) {

    fun getSetting(id: Long): Setting {
        return settingRepository.findById(id)
            .orElseThrow { throw EntityNotFoundException<Setting>(id) }
    }

    fun getMeasurements(settingId: Long): List<MeasurementDto> {
        return getSetting(settingId).measurements()
            .asSequence()
            .map { it.toDto() }
            .sortedBy { it.id }
            .toList()
    }

    fun exportMeasurements(settingId: Long): String {
        val setting = getSetting(settingId)
        val clazz = when (setting) {
            is WebPageTestSetting -> WebPageTestMeasurementDto::class.java
            is PageSpeedSetting -> PageSpeedMeasurementDto::class.java
        }

        val schema = csvMapper.schemaFor(clazz).rebuild()
            .setColumnSeparator('\t')
            .setUseHeader(true)
            .setStrictHeaders(true)
            .setNullValue("null")
            .build()

        val writer = csvMapper.writer(schema)
        return writer.writeValueAsString(setting.measurements().map { it.toDto() })
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
    fun setEnabled(id: Long, value: Boolean) {
        val setting = getSetting(id)
        if (setting.enabled xor value) {
            setting.apply {
                enabled = value
            }
            settingRepository.save(setting)
            if (value) {
                eventPublisher.publishEvent(RegisterEvent(this, setting))
            } else {
                eventPublisher.publishEvent(DeregisterEvent(this, id))
            }
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

    private fun <T : Setting> changeApiKey(setting: T, apiKeyId: Long) {
        val apiKey = apiKeyService.getApiKey(apiKeyId)
        when (setting) {
            is WebPageTestSetting -> setting.apiKey = castApiKey(apiKey)
            is PageSpeedSetting -> setting.apiKey = castApiKey(apiKey)
        }
    }

    private inline fun <reified T : ApiKey> castApiKey(apiKey: ApiKey): T = when (apiKey) {
        is T -> apiKey
        else -> throw InvalidApiTypeException("ApiKey with id = ${apiKey.id} is incompatible with provided type")
    }

    private fun <T : Setting> save(setting: T): T {
        if (CronSequenceGenerator.isValidExpression(setting.cronExpression)) {
            val saved = settingRepository.save(setting)
            eventPublisher.publishEvent(RegisterEvent(this, saved))
            return saved
        } else {
            throw InvalidCronExpressionException()
        }
    }

}
