package pl.pwr.maw.events

import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import pl.pwr.maw.setting.ScheduleService

@Component
class DeregisterEventListener(
    private val scheduleService: ScheduleService
) : ApplicationListener<DeregisterEvent> {

    override fun onApplicationEvent(event: DeregisterEvent) {
        scheduleService.deregister(event.settingsId)
    }

}
