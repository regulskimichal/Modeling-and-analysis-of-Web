package pl.pwr.maw.settings

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/settings")
@Tag(name = "Settings")
class SettingsController(
    private val settingsService: SettingsService
) {

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getSettings(@PathVariable id: Long): SettingsDto {
        return settingsService.getSettings(id).toDto()
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAllSettings(): List<SettingsDto> {
        return settingsService.getAllSettings().map { it.toDto() }
    }

    @PostMapping(consumes = [APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun saveSettings(@RequestBody settingsDao: SettingsDto): SettingsDto {
        return settingsService.saveSettings(settingsDao).toDto()
    }

    @PutMapping("/{id}", consumes = [APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateSettings(
        @PathVariable id: Long,
        @RequestBody settingsDto: SettingsDto
    ): SettingsDto {
        return settingsService.updateSettings(id, settingsDto).toDto()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSettings(@PathVariable id: Long) {
        settingsService.deleteSettings(id)
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAllSettings() {
        settingsService.deleteAllSettings()
    }

}
