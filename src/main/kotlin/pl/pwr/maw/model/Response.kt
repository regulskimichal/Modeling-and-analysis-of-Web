package pl.pwr.maw.model

import org.hibernate.annotations.Type
import javax.persistence.*

@Entity
@Table(name = "responses")
data class Response(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "responses_seq_gen")
    @SequenceGenerator(name = "responses_seq_gen", sequenceName = "responses_id_seq")
    @Column(unique = true, updatable = false, nullable = false)
    var id: Long?,

    @Type(type = "text")
    var value: String?

)
