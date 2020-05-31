package pl.pwr.maw.settings

import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.support.CronTrigger
import org.springframework.stereotype.Service
import pl.pwr.maw.commons.logger
import pl.pwr.maw.measurement.DefaultPerformanceMeasurer
import pl.pwr.maw.model.Setting
import java.util.TimeZone
import java.util.concurrent.ScheduledFuture

@Service
class ScheduleService(
    private val taskScheduler: TaskScheduler,
    private val performanceMeasurer: DefaultPerformanceMeasurer
) {

    private val scheduledTasks = mutableMapOf<Long, ScheduledFuture<*>>()

    fun register(setting: Setting) {
        val settingsId = setting.id

        if (settingsId != null) {
            val task = Runnable {
                log.debug("Running task")
                performanceMeasurer.preformMeasurement(setting)
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
