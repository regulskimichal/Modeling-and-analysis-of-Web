package pl.pwr.maw.measurement

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.pwr.maw.model.MeasurementDto

@RestController
@RequestMapping("/measurement")
@Tag(name = "Measurements")
class MeasurementController(
    private val measurementService: MeasurementService
) {

    @GetMapping("/{id}")
    @Operation(
        summary = "Get measurement by id",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Successful Operation",
                content = [Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = MeasurementDto::class)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Not found",
                content = [Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = MeasurementDto::class)
                )]
            )
        ]
    )
    fun getAllMeasurements(@PathVariable id: Long): MeasurementDto {
        return measurementService.getMeasurement(id).toDto()
    }

}
