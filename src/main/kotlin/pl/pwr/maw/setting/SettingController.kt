package pl.pwr.maw.setting

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.*
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

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getSetting(@PathVariable id: Long): SettingResponseDto {
        return settingService.getSetting(id).toDto()
    }

    @GetMapping("/{id}/measurements")
    @ResponseStatus(HttpStatus.OK)
    fun getMeasurementsForSetting(@PathVariable id: Long): List<MeasurementDto> {
        return settingService.getMeasurements(id)
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

    @PostMapping("/{id}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun enableSetting(@PathVariable id: Long) {
        settingService.setEnabled(id, true)
    }

    @PostMapping("/{id}/disable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun disableSetting(@PathVariable id: Long) {
        settingService.setEnabled(id, false)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSettings(@PathVariable id: Long) {
        settingService.deleteSetting(id)
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAllSettings() {
        settingService.deleteAllSettings()
    }

}
