package pl.pwr.maw.api

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.pwr.maw.commons.EntityNotFoundException
import pl.pwr.maw.commons.logger

@Service
@Transactional(readOnly = true)
class ApiKeyService(
    private val apiKeyRepository: ApiKeyRepository
) {

    fun getAllApiKeys(apiTypeName: String? = null): List<ApiKey> = when (apiTypeName) {
        null -> apiKeyRepository.findAllByTypeInOrderByIdAsc(ApiKeyType.values)
        else -> {
            val apiKeyType = ApiKeyType.values.firstOrNull { it.name.toLowerCase() == apiTypeName.toLowerCase() }
            if (apiKeyType != null) {
                apiKeyRepository.findAllByTypeOrderByIdAsc(apiKeyType)
            } else {
                throw ApiTypeNotFoundException(apiTypeName)
            }
        }
    }

    fun getApiKey(id: Long): ApiKey {
        return apiKeyRepository.findById(id).orElseThrow { EntityNotFoundException<ApiKey>(id) }
    }

    @Transactional
    fun saveApiKey(apiKey: ApiKey): ApiKey {
        if (apiKey.defaultKey) {
            changeDefaultKey(apiKey.type)
        }
        return apiKeyRepository.save(apiKey).also {
            log.debug("Saved API key: $apiKey")
        }
    }

    @Transactional
    fun updateApiKey(@ValidApiKey apiKey: ApiKey) {
        val persistedApiKey = getApiKey(apiKey.id!!)
        if (persistedApiKey.defaultKey != apiKey.defaultKey && apiKey.defaultKey) {
            changeDefaultKey(persistedApiKey.type)
        }
        persistedApiKey.also {
            it.name = apiKey.name
            it.apiKey = apiKey.apiKey
            it.defaultKey = apiKey.defaultKey
        }
        apiKeyRepository.save(persistedApiKey)
        log.debug("Updatelod API key: $persistedApiKey")
    }

    private fun changeDefaultKey(apiKeyType: ApiKeyType) {
        val currentDefaultApiKey = apiKeyRepository.findByTypeAndDefaultKeyIsTrue(apiKeyType)
        if (currentDefaultApiKey != null) {
            currentDefaultApiKey.defaultKey = false
            apiKeyRepository.save(currentDefaultApiKey)
        }
    }

    @Transactional
    fun deleteApiKey(id: Long) {
        val apiKey = apiKeyRepository.findByIdOrNull(id)
        if (apiKey != null) {
            if (!apiKey.defaultKey) {
                apiKeyRepository.deleteById(id)
            } else {
                throw DefaultApiKeyException(id)
            }
        } else {
            throw EntityNotFoundException<ApiKey>(id)
        }
    }

    companion object {
        private val log by logger<ApiKeyService>()
    }

}
