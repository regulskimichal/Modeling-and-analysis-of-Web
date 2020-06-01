package pl.pwr.maw.measurement

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.springframework.stereotype.Component
import pl.pwr.maw.commons.logger
import pl.pwr.maw.model.PageSpeedSetting
import pl.pwr.maw.model.Setting
import pl.pwr.maw.model.WebPageTestSetting

@Component
class DefaultPerformanceMeasurer(
    private val webPageTestMeasurer: WebPageTestMeasurer,
    private val pageSpeedMeasurer: PageSpeedMeasurer,
    private val measurementRepository: MeasurementRepository
) {

    fun preformMeasurement(setting: Setting) {
        GlobalScope.launch(IO) {
            val measurement = when (setting) {
                is WebPageTestSetting -> webPageTestMeasurer.preformMeasurement(setting)
                is PageSpeedSetting -> pageSpeedMeasurer.preformMeasurement(setting)
            }
            val saved = measurementRepository.save(measurement)
            log.debug(saved.toString())
        }
    }

    companion object {
        val log by logger<DefaultPerformanceMeasurer>()
    }

}
