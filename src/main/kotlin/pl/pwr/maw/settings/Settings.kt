package pl.pwr.maw.settings

import pl.pwr.maw.api.Api
import java.time.ZoneId
import javax.persistence.*

@Entity
@Table(name = "settings")
data class Settings(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null,

    var pageUrl: String,

    @ManyToOne
    var api: Api,

    var strategy: Strategy,

    var cronExpression: String,

    var zoneId: ZoneId,

    var jsonPathExpression: String? = null
) {
    fun toDto() = SettingsDto(id, pageUrl, api.id, strategy, cronExpression, zoneId, jsonPathExpression)
}
