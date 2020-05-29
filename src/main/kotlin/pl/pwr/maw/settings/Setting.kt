package pl.pwr.maw.settings

import pl.pwr.maw.api.ApiKey
import pl.pwr.maw.api.WebPageTestApiKey
import java.time.ZoneId
import javax.persistence.*

@Entity
@Table(name = "settings")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
sealed class Setting(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    open var id: Long? = null,

    open var pageUrl: String,

    open var cronExpression: String,

    open var zoneId: ZoneId
) : ApiKeyProvider

@Entity
@Table(name = "web_page_test_settings")
data class WebPageTestSetting(
    override var id: Long? = null,

    override var pageUrl: String,

    @ManyToOne
    override var apiKey: WebPageTestApiKey,

    override var cronExpression: String,

    override var zoneId: ZoneId
) : Setting(id, pageUrl, cronExpression, zoneId)

@Entity
@Table(name = "page_speed_settings")
data class PageSpeedSetting(
    override var id: Long? = null,

    override var pageUrl: String,

    @ManyToOne
    override var apiKey: ApiKey,

    override var cronExpression: String,

    override var zoneId: ZoneId
) : Setting(id, pageUrl, cronExpression, zoneId)
