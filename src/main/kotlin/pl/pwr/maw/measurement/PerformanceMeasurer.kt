package pl.pwr.maw.measurement

import pl.pwr.maw.model.Measurement
import pl.pwr.maw.model.Setting

interface PerformanceMeasurer<T : Setting> {
    suspend fun preformMeasurement(setting: T): Measurement
}
