package pl.pwr.maw.model

import java.time.ZoneId
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
sealed class Setting(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(updatable = false, nullable = false)
    open var id: Long? = null,

    open var pageUrl: String,

    open var cronExpression: String,

    open var zoneId: ZoneId

) {

    abstract fun getApiKey(): ApiKey

    abstract fun measurements(): Set<Measurement>

    abstract fun toDto(): SettingResponseDto

}

@Entity
@Table(name = "web_page_test_settings")
data class WebPageTestSetting(
    override var id: Long? = null,
    override var pageUrl: String,
    override var cronExpression: String,
    override var zoneId: ZoneId
) : Setting(id, pageUrl, cronExpression, zoneId) {

    @ManyToOne
    lateinit var apiKey: WebPageTestApiKey

    @OneToMany(mappedBy = "setting")
    lateinit var measurements: Set<WebPageTestMeasurement>

    override fun getApiKey(): ApiKey = apiKey

    override fun measurements(): Set<Measurement> = measurements

    override fun toDto() = WebPageTestSettingResponseDto(
        id,
        pageUrl,
        apiKey.id,
        cronExpression,
        zoneId
    )

}

@Entity
@Table(name = "page_speed_settings")
data class PageSpeedSetting(
    override var id: Long? = null,
    override var pageUrl: String,
    override var cronExpression: String,
    override var zoneId: ZoneId,
    @Enumerated(EnumType.STRING) var strategy: Strategy?
) : Setting(id, pageUrl, cronExpression, zoneId) {

    @ManyToOne
    lateinit var apiKey: PageSpeedApiKey

    @OneToMany(mappedBy = "setting")
    lateinit var measurements: Set<PageSpeedMeasurement>

    override fun getApiKey(): ApiKey = apiKey

    override fun measurements(): Set<Measurement> = measurements

    override fun toDto() = PageSpeedSettingResponseDto(id, pageUrl, apiKey.id, cronExpression, zoneId)

}
