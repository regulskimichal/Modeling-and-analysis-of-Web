package pl.pwr.maw.measurementsettings

import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.*
import pl.pwr.maw.utils.MediaType.Companion.APPLICATION_JSON_PATCH_VALUE
import javax.json.JsonPatch

@RestController
@RequestMapping("/measurement-settings")
class MeasurementSettingsController(
    private val measurementSettingsService: MeasurementSettingsService
) {

    @GetMapping("/{id}")
    suspend fun getPageMeasurementSettings(@PathVariable id: Long): MeasurementSettings? {
        return measurementSettingsService.getMeasurementSettings(id)
    }

    @GetMapping
    fun getAllPageMeasurementSettings(): Flow<MeasurementSettings> {
        return measurementSettingsService.getAllMeasurementSettings()
    }

    @PostMapping(consumes = [APPLICATION_JSON_VALUE])
    suspend fun savePageMeasurementSettings(@RequestBody settingsDao: MeasurementSettingsDto) {
        measurementSettingsService.saveMeasurementSettings(settingsDao.toEntity())
    }

    @PatchMapping("/{id}", consumes = [APPLICATION_JSON_PATCH_VALUE])
    suspend fun modifyPageMeasurementSettings(@PathVariable id: Long, @RequestBody patch: JsonPatch) {
        measurementSettingsService.updateMeasurementSettings(id, patch)
    }

    @DeleteMapping("/{id}")
    suspend fun deletePageMeasurementSettings(@PathVariable id: Long) {
        measurementSettingsService.deleteMeasurementSettings(id)
    }

    @DeleteMapping
    suspend fun deleteAllPageMeasurementSettings() {
        measurementSettingsService.deleteAllMeasurementSettings()
    }

}
