package pl.pwr.maw.settings

import pl.pwr.maw.api.ApiKey
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

    @ManyToOne
    open var apiKey: ApiKey,

    open var cronExpression: String,

    open var zoneId: ZoneId
) {
    abstract fun toDto(): SettingResponseDto
}

@Entity
@Table(name = "web_page_test_settings")
data class WebPageTestSetting(
    override var id: Long? = null,

    override var pageUrl: String,

    @ManyToOne
    override var apiKey: ApiKey,

    override var cronExpression: String,

    override var zoneId: ZoneId
) : Setting(id, pageUrl, apiKey, cronExpression, zoneId) {
    override fun toDto() = WebPageTestSettingResponseDto(id, pageUrl, apiKey.id, cronExpression, zoneId)
}

@Entity
@Table(name = "page_speed_settings")
data class PageSpeedSetting(
    override var id: Long? = null,

    override var pageUrl: String,

    @ManyToOne
    override var apiKey: ApiKey,

    override var cronExpression: String,

    override var zoneId: ZoneId
) : Setting(id, pageUrl, apiKey, cronExpression, zoneId) {
    override fun toDto() = PageSpeedSettingResponseDto(id, pageUrl, apiKey.id, cronExpression, zoneId)
}
