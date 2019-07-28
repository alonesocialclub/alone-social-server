package social.alone.server.link

import social.alone.server.event.domain.Event
import lombok.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp

import javax.persistence.*
import java.time.LocalDateTime

@Getter
@EqualsAndHashCode(of = ["id"])
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Link {


    @Id
    @GeneratedValue
    val id: Long? = null

    @ManyToOne
    val event: Event

    @CreationTimestamp
    val createdAt: LocalDateTime? = null

    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null
        protected set

    @Builder
    constructor(event: Event) {
        this.event = event
    }

}
