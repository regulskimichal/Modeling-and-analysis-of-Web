package pl.pwr.maw.settings

import pl.pwr.maw.api.ApiKey
import pl.pwr.maw.api.PageSpeedApiKey
import pl.pwr.maw.api.WebPageTestApiKey
import pl.pwr.maw.measurement.Measurement
import pl.pwr.maw.measurement.PageSpeedMeasurement
import pl.pwr.maw.measurement.WebPageTestMeasurement
import java.time.ZoneId
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
sealed class Setting(
    open var pageUrl: String,
    open var cronExpression: String,
    open var zoneId: ZoneId
) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    open var id: Long? = null

    abstract fun apiKey(): ApiKey

    abstract fun measurements(): Set<Measurement>

    abstract fun toDto(): SettingResponseDto
}

@Entity
@Table(name = "web_page_test_settings")
data class WebPageTestSetting(
    override var pageUrl: String,
    override var cronExpression: String,
    override var zoneId: ZoneId
) : Setting(pageUrl, cronExpression, zoneId) {
    override var id: Long? = null

    @ManyToOne
    lateinit var apiKey: WebPageTestApiKey

    @OneToMany
    lateinit var measurements: Set<WebPageTestMeasurement>

    override fun apiKey(): ApiKey = apiKey

    override fun measurements(): Set<Measurement> = measurements

    override fun toDto() = WebPageTestSettingResponseDto(id, pageUrl, apiKey.id, cronExpression, zoneId)
}

@Entity
@Table(name = "page_speed_settings")
data class PageSpeedSetting(
    override var pageUrl: String,
    override var cronExpression: String,
    override var zoneId: ZoneId,
    @Enumerated(EnumType.STRING)
    var strategy: Strategy?
) : Setting(pageUrl, cronExpression, zoneId) {
    override var id: Long? = null

    @ManyToOne
    lateinit var apiKey: PageSpeedApiKey

    @OneToMany
    lateinit var measurements: Set<PageSpeedMeasurement>

    override fun apiKey(): ApiKey = apiKey

    override fun measurements(): Set<Measurement> = measurements

    override fun toDto() = PageSpeedSettingResponseDto(id, pageUrl, apiKey.id, cronExpression, zoneId)
}
