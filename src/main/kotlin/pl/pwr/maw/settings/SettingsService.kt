package pl.pwr.maw.settings

import org.slf4j.LoggerFactory
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.support.CronSequenceGenerator
import org.springframework.scheduling.support.CronTrigger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.pwr.maw.api.ApiService
import pl.pwr.maw.exception.EntityNotFoundException
import java.util.TimeZone
import java.util.concurrent.ScheduledFuture

@Service
@Transactional(readOnly = true)
class SettingsService(
    private val apiService: ApiService,
    private val taskScheduler: TaskScheduler,
    private val settingsRepository: SettingsRepository
) {

    private val scheduledTasks = mutableMapOf<Long, ScheduledFuture<*>>()

    @EventListener(ContextRefreshedEvent::class)
    fun onContextRefreshedEvent() {
        getAllSettings().forEach { register(it) }
    }

    fun getSettings(id: Long): Settings {
        return settingsRepository.findById(id)
            .orElseThrow { throw EntityNotFoundException<Settings>(id) }
    }

    fun getAllSettings(): List<Settings> {
        return settingsRepository.findAll()
    }

    @Transactional
    fun saveSettings(settingsDto: SettingsDto): Settings {
        val apis = apiService.getApis(settingsDto.apis)
        return save(settingsDto.toEntity(apis))
    }

    @Transactional
    fun updateSettings(id: Long, settingsDto: SettingsDto): Settings {
        if (settingsRepository.existsById(id)) {
            val apis = apiService.getApis(settingsDto.apis)
            return save(settingsDto.toEntity(apis, id))
        } else {
            throw EntityNotFoundException<Settings>(id)
        }
    }

    @Transactional
    fun deleteSettings(id: Long) {
        if (settingsRepository.existsById(id)) {
            settingsRepository.deleteById(id)
            unregister(id)
        } else {
            throw EntityNotFoundException<Settings>(id)
        }
    }

    @Transactional
    fun deleteAllSettings() {
        settingsRepository.deleteAll()
    }

    private fun save(settings: Settings): Settings {
        if (CronSequenceGenerator.isValidExpression(settings.cronExpression)) {
            val saved = settingsRepository.save(settings)
            register(settings)
            return saved
        } else {
            throw IllegalArgumentException("Invalid value of cronExpression")
        }
    }

    private fun register(settings: Settings) {
        val settingsId = settings.id

        if (settingsId != null) {
            val task = Runnable {
                log.debug("Running task")
                //TODO
                //webPageTestMeasurer.performMeasurement(settings.pageUrl)
            }

            val trigger = CronTrigger(settings.cronExpression, TimeZone.getTimeZone(settings.zoneId))
            val scheduledTask = taskScheduler.schedule(task, trigger)
            if (scheduledTask != null) {
                scheduledTasks[settingsId] = scheduledTask
            }
        }
    }

    private fun unregister(id: Long) {
        scheduledTasks.remove(id)?.cancel(false)
    }

    companion object {
        private val log = LoggerFactory.getLogger(SettingsService::class.java)
    }

}
