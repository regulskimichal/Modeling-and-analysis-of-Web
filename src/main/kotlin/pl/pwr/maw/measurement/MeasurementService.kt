package pl.pwr.maw.measurement

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.pwr.maw.commons.EntityNotFoundException

@Service
@Transactional(readOnly = true)
class MeasurementService(
    private val measurementRepository: MeasurementRepository
) {

    fun getMeasurement(id: Long): Measurement {
        return measurementRepository.findByIdOrNull(id) ?: throw EntityNotFoundException<Measurement>(id)
    }

}
