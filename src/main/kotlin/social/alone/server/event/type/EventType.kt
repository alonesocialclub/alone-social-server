package social.alone.server.event.type

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.AccessLevel
import lombok.EqualsAndHashCode
import lombok.Getter
import lombok.NoArgsConstructor
import social.alone.server.event.domain.Event
import java.util.*
import javax.persistence.*

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = [Index(name = "idx_value", columnList = "value", unique = true)])
@EqualsAndHashCode(of = ["id"])
class EventType(@Column(nullable = false, unique = true) val value: String) {

    @Id
    @GeneratedValue
    val id: Long? = null

    @ManyToMany(mappedBy = "eventTypes")
    @JsonIgnore
    val events: Set<Event> = HashSet()

    fun toDto(): EventTypeDto {
        return EventTypeDto(this.id, this.value)
    }

    companion object {
        fun of(value:String) : EventType = EventType(value)
    }

}