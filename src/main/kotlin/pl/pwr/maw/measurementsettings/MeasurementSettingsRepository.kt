package pl.pwr.maw.measurementsettings

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MeasurementSettingsRepository : ReactiveCrudRepository<MeasurementSettings, Long>
