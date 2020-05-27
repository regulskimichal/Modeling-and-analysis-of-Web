package pl.pwr.maw.measurement

import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Service
import pl.pwr.maw.settings.SettingsService

@Service
class SchedulingRegisterService(
    private val taskScheduler: TaskScheduler,
    private val pageSpeedMeasurer: PageSpeedMeasurer,
    private val webPageTestMeasurer: WebPageTestMeasurer,
    private val settingsService: SettingsService
)
