package pl.pwr.maw.events

import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import pl.pwr.maw.setting.ScheduleService
import pl.pwr.maw.setting.SettingService

@Component
class ContextRefreshedEventListener(
    private val scheduleService: ScheduleService,
    private val settingService: SettingService
) : ApplicationListener<ContextRefreshedEvent> {

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        settingService.getAllSettings().forEach {
            if (it.enabled) {
                scheduleService.register(it)
            }
        }
    }

}
