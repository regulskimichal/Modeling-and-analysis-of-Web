package pl.pwr.maw.settings

import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

@Component
class ContextRefreshedEventListener(
    private val scheduleService: ScheduleService,
    private val settingService: SettingService
) : ApplicationListener<ContextRefreshedEvent> {

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        settingService.getAllSettings().forEach { scheduleService.register(it) }
    }

}
