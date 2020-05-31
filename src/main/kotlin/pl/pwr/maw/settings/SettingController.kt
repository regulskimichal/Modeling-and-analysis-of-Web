package pl.pwr.maw.settings

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/setting")
@Tag(name = "Settings")
class SettingController(
    private val settingService: SettingService
) {

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getSettings(@PathVariable id: Long): SettingResponseDto {
        return settingService.getSetting(id).toDto()
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAllSettings(): List<SettingResponseDto> {
        return settingService.getAllSettings().map { it.toDto() }
    }

    @PostMapping(consumes = [APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun saveSettings(@RequestBody settingDto: SettingDto): SettingResponseDto {
        return settingService.saveSetting(settingDto.toEntity(), settingDto.apiKeyId).toDto()
    }

    @PutMapping("/{id}", consumes = [APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateSettings(
        @PathVariable id: Long,
        @RequestBody settingDto: SettingDto
    ): SettingResponseDto {
        return settingService.updateSetting(id, settingDto.toEntity(), settingDto.apiKeyId).toDto()
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
