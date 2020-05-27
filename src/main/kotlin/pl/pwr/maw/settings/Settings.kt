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

    @ManyToMany(cascade = [CascadeType.MERGE, CascadeType.PERSIST])
    @JoinTable(
        joinColumns = [JoinColumn(name = "setting_id")],
        inverseJoinColumns = [JoinColumn(name = "api_id")]
    )
    var apis: Set<Api>,

    var strategy: Strategy,

    var cronExpression: String,

    var zoneId: ZoneId,

    var jsonPathExpression: String? = null
) {
    fun toDto() = SettingsDto(id, pageUrl, apis.map { it.id }, strategy, cronExpression, zoneId, jsonPathExpression)
}
