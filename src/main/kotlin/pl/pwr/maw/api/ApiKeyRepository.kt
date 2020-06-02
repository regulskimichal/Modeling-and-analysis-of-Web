package pl.pwr.maw.api

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.pwr.maw.model.ApiKey
import pl.pwr.maw.model.ApiType

@Repository
interface ApiKeyRepository : JpaRepository<ApiKey, Long> {
    fun findAllByTypeInOrderByIdAsc(types: Collection<ApiType>): List<ApiKey>
    fun findAllByTypeOrderByIdAsc(types: ApiType): List<ApiKey>
    fun findByTypeAndDefaultKeyIsTrue(type: ApiType): ApiKey?
}
