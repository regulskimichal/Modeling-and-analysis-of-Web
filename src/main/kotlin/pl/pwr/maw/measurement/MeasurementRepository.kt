package pl.pwr.maw.measurement

import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface MeasurementRepository : ReactiveCrudRepository<Measurement, Long>
