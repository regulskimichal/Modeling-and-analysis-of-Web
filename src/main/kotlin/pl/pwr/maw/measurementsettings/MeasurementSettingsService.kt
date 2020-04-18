package pl.pwr.maw.measurementsettings

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.scheduling.support.CronSequenceGenerator
import org.springframework.stereotype.Service
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait
import pl.pwr.maw.utils.applyOn
import javax.json.JsonException
import javax.json.JsonPatch

@Service
class MeasurementSettingsService(
    private val objectMapper: ObjectMapper,
    private val transactionalOperator: TransactionalOperator,
    private val measurementSettingsRepository: MeasurementSettingsRepository
) {

    suspend fun getMeasurementSettings(id: Long): MeasurementSettings? {
        return transactionalOperator.executeAndAwait {
            measurementSettingsRepository.findById(id).awaitFirstOrNull()
        }
    }

    fun getAllMeasurementSettings(): Flow<MeasurementSettings> {
        return transactionalOperator.execute {
            measurementSettingsRepository.findAll()
        }.asFlow()
    }

    suspend fun saveMeasurementSettings(measurementSettings: MeasurementSettings) {
        transactionalOperator.executeAndAwait {
            save(measurementSettings)
        }
    }

    suspend fun updateMeasurementSettings(id: Long, patch: JsonPatch) {
        transactionalOperator.executeAndAwait {
            val measurementSettings = measurementSettingsRepository.findById(id).awaitFirstOrNull()
                ?: throw NoSuchElementException("MeasurementSetting with id $id does not exist")

            try {
                val patched = patch.applyOn(objectMapper, measurementSettings.toDto())
                save(patched.toEntity(id))
            } catch (e: JsonException) {
                throw IllegalArgumentException("A patch is malformed or contains non-existing attributes")
            }
        }
    }

    private suspend fun save(measurementSettings: MeasurementSettings) {
        if (CronSequenceGenerator.isValidExpression(measurementSettings.cronExpression)) {
            measurementSettingsRepository.save(measurementSettings).awaitFirstOrNull()
        } else {
            throw IllegalArgumentException("Invalid value of cronExpression")
        }
    }

    suspend fun deleteMeasurementSettings(id: Long) {
        transactionalOperator.executeAndAwait {
            measurementSettingsRepository.deleteById(id).awaitFirstOrNull()
        }
    }

    suspend fun deleteAllMeasurementSettings() {
        transactionalOperator.executeAndAwait {
            measurementSettingsRepository.deleteAll().awaitFirstOrNull()
        }
    }

}
