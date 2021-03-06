package pl.pwr.maw.setting

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.scheduling.support.CronTrigger
import org.springframework.stereotype.Service
import pl.pwr.maw.commons.logger
import pl.pwr.maw.measurement.DefaultPerformanceMeasurer
import pl.pwr.maw.model.Setting
import java.util.TimeZone
import java.util.concurrent.ScheduledFuture

@Service
class ScheduleService(

    private val taskScheduler: ThreadPoolTaskScheduler,
    private val performanceMeasurer: DefaultPerformanceMeasurer
) {

    private val scheduledTasks = mutableMapOf<Long, ScheduledFuture<*>>()

    fun register(setting: Setting) {
        val settingsId = setting.id

        if (settingsId != null) {
            val task = Runnable {
                log.debug("Performance measure for $setting")
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
