package pl.pwr.maw.measurement

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.pwr.maw.model.Measurement

@Repository
interface MeasurementRepository : JpaRepository<Measurement, Long>
