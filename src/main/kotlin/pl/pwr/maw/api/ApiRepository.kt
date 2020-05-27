package pl.pwr.maw.api

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ApiRepository : JpaRepository<Api, Long>
