package pl.pwr.maw.model

import java.time.ZoneId
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
sealed class Setting(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "settings_seq_gen")
    @SequenceGenerator(name = "settings_seq_gen", sequenceName = "settings_id_seq")
    @Column(unique = true, updatable = false, nullable = false)
    open var id: Long? = null,

    open var pageUrl: String,

    open var cronExpression: String,

    open var zoneId: ZoneId,

    @Column(nullable = false)
    open var enabled: Boolean

) {

    @ManyToOne
    open var apiKey: ApiKey? = null

    abstract fun measurements(): Set<Measurement>

    abstract fun toDto(): SettingResponseDto

}

@Entity
@Table(name = "web_page_test_settings")
data class WebPageTestSetting(
    override var id: Long? = null,
    override var pageUrl: String,
    override var cronExpression: String,
    override var zoneId: ZoneId,
    override var enabled: Boolean,
    @Embedded var browserConfiguration: BrowserConfiguration?
) : Setting(id, pageUrl, cronExpression, zoneId, enabled) {

    @ManyToOne
    override var apiKey: ApiKey? = null

    @OneToMany(mappedBy = "setting")
    lateinit var measurements: Set<WebPageTestMeasurement>

    override fun measurements(): Set<Measurement> = measurements

    override fun toDto() = WebPageTestSettingResponseDto(
        id,
        pageUrl,
        apiKey?.id,
        cronExpression,
        zoneId,
        enabled,
        browserConfiguration
    )

}

@Entity
@Table(name = "page_speed_settings")
data class PageSpeedSetting(
    override var id: Long? = null,
    override var pageUrl: String,
    override var cronExpression: String,
    override var zoneId: ZoneId,
    override var enabled: Boolean,
    @Enumerated(EnumType.STRING) var strategy: Strategy?
) : Setting(id, pageUrl, cronExpression, zoneId, enabled) {

    @ManyToOne
    override var apiKey: ApiKey? = null

    @OneToMany(mappedBy = "setting")
    lateinit var measurements: Set<PageSpeedMeasurement>

    override fun measurements(): Set<Measurement> = measurements

    override fun toDto() = PageSpeedSettingResponseDto(
        id,
        pageUrl,
        apiKey?.id,
        cronExpression,
        zoneId,
        enabled,
        strategy
    )

}
