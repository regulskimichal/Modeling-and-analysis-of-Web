package pl.pwr.maw.settings

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/settings")
@Tag(name = "Settings")
class SettingController(
    private val settingService: SettingService
) {

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getSettings(@PathVariable id: Long): SettingDto {
        return TODO() // settingService.getSetting(id).toDto()
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAllSettings(): List<SettingDto> {
        return TODO() // settingService.getAllSettings().map { it.toDto() }
    }

    @PostMapping(consumes = [APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun saveSettings(@RequestBody settingDao: SettingDto): SettingDto {
        return TODO() // settingService.saveSetting(settingDao).toDto()
    }

    @PutMapping("/{id}", consumes = [APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateSettings(
        @PathVariable id: Long,
        @RequestBody settingDto: SettingDto
    ): SettingDto {
        return TODO() // settingService.updateSetting(id, settingDto).toDto()
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
