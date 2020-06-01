package pl.pwr.maw.model

import pl.pwr.maw.model.ApiType.*
import javax.persistence.*

@Entity
@Table(name = "api_keys")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
sealed class ApiKey(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(updatable = false, nullable = false)
    open var id: Long?,

    @Column(unique = true, nullable = false)
    open var name: String,

    @Column(unique = true, nullable = false)
    open var apiKey: String,

    @Column(nullable = false)
    open var defaultKey: Boolean,

    @Enumerated(EnumType.STRING)
    @Column(name = "type", insertable = false, updatable = false, nullable = false)
    open var type: ApiType

)

@Entity
@DiscriminatorValue(value = ApiType.WEB_PAGE_TEST_NAME)
data class WebPageTestApiKey(
    override var id: Long? = null,
    override var name: String,
    override var apiKey: String,
    override var defaultKey: Boolean = false,
    override var type: ApiType = WEB_PAGE_TEST
) : ApiKey(id, name, apiKey, defaultKey, type)

@Entity
@DiscriminatorValue(value = ApiType.PAGE_SPEED_NAME)
data class PageSpeedApiKey(
    override var id: Long? = null,
    override var name: String,
    override var apiKey: String,
    override var defaultKey: Boolean = false,
    override var type: ApiType = PAGE_SPEED
) : ApiKey(id, name, apiKey, defaultKey, type)
