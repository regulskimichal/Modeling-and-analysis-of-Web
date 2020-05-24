package pl.pwr.maw.apikey

import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait
import pl.pwr.maw.measurementsettings.Api

@Service
class ApiSettingsService(
    private val transactionalOperator: TransactionalOperator,
    private val apiSettingsRepository: ApiSettingsRepository
) {

    suspend fun getApiKey(providerType: Api): String? {
        return transactionalOperator.executeAndAwait {
            apiSettingsRepository.findById(providerType.name).awaitFirstOrNull()?.apiKey
        }
    }

    suspend fun saveApiKey(apiSettings: ApiSettings) {
        transactionalOperator.executeAndAwait {
            if (isApiName(apiSettings.apiName)) {
                apiSettingsRepository.save(apiSettings).then().awaitFirstOrNull()
            } else {
                throw NoSuchApiDefinedException()
            }
        }
    }

    private fun isApiName(name: String) = name in Api.values().map(Api::name)

}
