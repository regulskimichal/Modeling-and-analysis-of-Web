package pl.pwr.maw.api

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "apis")
data class Api(
    @Id var id: Long,
    var name: String,
    var apiKey: String
) {

    companion object {
        const val PAGE_SPEED_ID = 1L
        const val WEB_PAGE_TEST_ID = 2L
        val AVAILABLE_API_IDS = longArrayOf(PAGE_SPEED_ID, WEB_PAGE_TEST_ID)
    }

}
