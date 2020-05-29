package pl.pwr.maw.api

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ApiKeyRepository : JpaRepository<ApiKey, Long> {
    fun findAllByTypeInOrderByIdAsc(types: Collection<ApiKeyType>): List<ApiKey>
    fun findAllByTypeOrderByIdAsc(types: ApiKeyType): List<ApiKey>
    fun findByTypeAndDefaultKeyIsTrue(type: ApiKeyType): ApiKey?
}
