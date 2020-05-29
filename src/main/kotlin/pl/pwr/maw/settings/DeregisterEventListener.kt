package pl.pwr.maw.settings

import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import pl.pwr.maw.commons.events.RegisterEvent

@Component
class DeregisterEventListener(
    private val scheduleService: ScheduleService
) : ApplicationListener<RegisterEvent> {

    override fun onApplicationEvent(event: RegisterEvent) {
        scheduleService.register(event.setting)
    }

}
