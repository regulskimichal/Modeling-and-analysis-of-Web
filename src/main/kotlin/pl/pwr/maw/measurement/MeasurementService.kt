package pl.pwr.maw.measurement

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.pwr.maw.commons.EntityNotFoundException
import pl.pwr.maw.model.Measurement

@Service
@Transactional(readOnly = true)
class MeasurementService(
    private val measurementRepository: MeasurementRepository
) {

    fun getMeasurement(id: Long): Measurement {
        return measurementRepository.findById(id).orElseThrow { EntityNotFoundException<Measurement>(id) }
    }

}
