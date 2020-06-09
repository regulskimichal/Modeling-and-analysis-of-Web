package pl.pwr.maw.setting

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.MediaType.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.pwr.maw.model.MeasurementDto
import pl.pwr.maw.model.SettingDto
import pl.pwr.maw.model.SettingResponseDto

@RestController
@RequestMapping("/setting")
@Tag(name = "Settings")
class SettingController(
    private val settingService: SettingService
) {

    @GetMapping("/{settingId}")
    @ResponseStatus(HttpStatus.OK)
    fun getSetting(@PathVariable settingId: Long): SettingResponseDto {
        return settingService.getSetting(settingId).toDto()
    }

    @GetMapping("/{settingId}/measurements")
    @ResponseStatus(HttpStatus.OK)
    fun getMeasurementsForSetting(@PathVariable settingId: Long): List<MeasurementDto> {
        return settingService.getMeasurements(settingId)
    }

    @GetMapping("/{settingId}/measurements/export")
    @ResponseStatus(HttpStatus.OK)
    fun exportMeasurementsToCsvForSetting(@PathVariable settingId: Long): ResponseEntity<String> {
        val headers = HttpHeaders().apply {
            contentType = MediaType("text", "csv")
            setContentDispositionFormData("attachment", "measurements_$settingId.csv")
        }

        return ResponseEntity(settingService.exportMeasurements(settingId), headers, HttpStatus.OK)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAllSettings(): List<SettingResponseDto> {
        return settingService.getAllSettings().map { it.toDto() }
    }

    @PostMapping(consumes = [APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun saveSetting(@RequestBody settingDto: SettingDto): SettingResponseDto {
        return settingService.saveSetting(settingDto.toEntity(), settingDto.apiKeyId).toDto()
    }

    @PostMapping("/{settingId}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun enableSetting(@PathVariable settingId: Long) {
        settingService.setEnabled(settingId, true)
    }

    @PostMapping("/{settingId}/disable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun disableSetting(@PathVariable settingId: Long) {
        settingService.setEnabled(settingId, false)
    }

    @DeleteMapping("/{settingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSettings(@PathVariable settingId: Long) {
        settingService.deleteSetting(settingId)
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAllSettings() {
        settingService.deleteAllSettings()
    }

}
