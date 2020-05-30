package pl.pwr.maw.settings

import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.support.CronTrigger
import org.springframework.stereotype.Service
import pl.pwr.maw.commons.logger
import java.util.TimeZone
import java.util.concurrent.ScheduledFuture

@Service
class ScheduleService(
    private val taskScheduler: TaskScheduler
) {

    private val scheduledTasks = mutableMapOf<Long, ScheduledFuture<*>>()

    fun register(setting: Setting) {
        val settingsId = setting.id

        if (settingsId != null) {
            val task = Runnable {
                log.debug("Running task")
                //TODO
                //webPageTestMeasurer.performMeasurement(settings.pageUrl)
            }

            val trigger = CronTrigger(setting.cronExpression, TimeZone.getTimeZone(setting.zoneId))
            val scheduledTask = taskScheduler.schedule(task, trigger)
            if (scheduledTask != null) {
                scheduledTasks[settingsId] = scheduledTask
            }
        }
    }

    fun deregister(id: Long) {
        scheduledTasks.remove(id)?.cancel(false)
    }

    companion object {
        private val log by logger<ScheduleService>()
    }

}
