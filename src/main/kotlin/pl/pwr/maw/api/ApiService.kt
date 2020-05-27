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

    fun getApis(): List<Api> {
        return apiRepository.findAll()
    }

    fun getApis(ids: List<Long>): Set<Api> {
        return apiRepository.findAllById(ids).toSet()
    }

    @Transactional
    fun saveApiKey(id: Long, apiKeyDto: ApiKeyDto): Api {
        val api: Api = apiRepository.findById(id).orElseThrow {
            EntityNotFoundException<Api>(
                id
            )
        }
        api.apiKey = apiKeyDto.apiKey
        val saved = apiRepository.save(api)
        log.debug("Updated API key: $saved")
        return saved
    }

    companion object {
        private val log = LoggerFactory.getLogger(ApiService::class.java)
    }

}
