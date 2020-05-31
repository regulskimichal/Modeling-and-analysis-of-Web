package pl.pwr.maw.measurement

import org.springframework.stereotype.Component
import pl.pwr.maw.model.Measurement
import pl.pwr.maw.model.PageSpeedSetting
import pl.pwr.maw.model.Setting
import pl.pwr.maw.model.WebPageTestSetting

@Component
class DefaultPerformanceMeasurer(
    private val webPageTestMeasurer: WebPageTestMeasurer,
    private val pageSpeedMeasurer: PageSpeedMeasurer,
    private val measurementRepository: MeasurementRepository
) : PerformanceMeasurer<Setting> {

    override fun preformMeasurement(setting: Setting): Measurement {
        val measurement = when (setting) {
            is WebPageTestSetting -> webPageTestMeasurer.preformMeasurement(setting)
            is PageSpeedSetting -> pageSpeedMeasurer.preformMeasurement(setting)
        }
        return measurementRepository.save(measurement)
    }

}
