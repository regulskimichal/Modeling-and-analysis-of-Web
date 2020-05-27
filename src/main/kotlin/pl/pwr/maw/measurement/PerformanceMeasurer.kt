package pl.pwr.maw.measurement

interface PerformanceMeasurer {

    fun preformMeasurement(url: String, runs: Int = 1, firstViewOnly: Boolean = false): MeasurementResult?

}
