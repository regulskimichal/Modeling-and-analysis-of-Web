package pl.pwr.maw.measurement

import pl.pwr.maw.settings.Setting

interface PerformanceMeasurer<T : Setting> {
    fun preformMeasurement(setting: T): Measurement
}
