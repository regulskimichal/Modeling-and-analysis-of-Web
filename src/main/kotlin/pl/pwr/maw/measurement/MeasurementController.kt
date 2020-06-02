package pl.pwr.maw.measurement

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.pwr.maw.model.MeasurementDto

@RestController
@RequestMapping("/measurement")
class MeasurementController(
    private val measurementService: MeasurementService
) {

    @GetMapping("/{id}")
    fun getAllMeasurements(@PathVariable id: Long): MeasurementDto {
        return measurementService.getMeasurement(id).toDto()
    }

}
