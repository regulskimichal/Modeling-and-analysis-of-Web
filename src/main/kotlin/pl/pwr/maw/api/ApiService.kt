package pl.pwr.maw.api

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.pwr.maw.exception.EntityNotFoundException

@Service
@Transactional(readOnly = true)
class ApiService(
    private val apiRepository: ApiRepository
) {

    fun getAllApi(): List<Api> {
        return apiRepository.findAllByOrderByIdAsc()
    }

    fun getApi(id: Long): Api {
        return apiRepository.findById(id).orElseThrow { EntityNotFoundException<Api>(id) }
    }

    @Transactional
    fun saveApiKey(id: Long, apiKeyDto: ApiKeyDto): Api {
        return getApi(id).also {
            it.apiKey = apiKeyDto.apiKey
            apiRepository.save(it)
            log.debug("Updated API key: $it")
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(ApiService::class.java)
    }

}
