package pl.pwr.maw.events

import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import pl.pwr.maw.setting.ScheduleService

@Component
class RegisterEventListener(
    private val scheduleService: ScheduleService
) : ApplicationListener<RegisterEvent> {

    override fun onApplicationEvent(event: RegisterEvent) {
        scheduleService.register(event.setting)
    }

}
