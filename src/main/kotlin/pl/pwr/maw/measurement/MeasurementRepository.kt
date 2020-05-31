package pl.pwr.maw.measurement

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MeasurementRepository : JpaRepository<Measurement, Long>
