package social.alone.server.event.type

import lombok.Getter
import lombok.Setter

import java.time.LocalDateTime
import java.util.Optional

@Getter
class EventQueryParams {

    var type: EventQueryType? = EventQueryType.ALL
        set(type) {
            field = type ?: EventQueryType.ALL
        }

    @Setter
    var longitude: Double? = null
        set(longitude) {
            field = this.longitude
        }

    @Setter
    var latitude: Double? = null
        set(latitude) {
            field = this.latitude
        }

    val startedAt: LocalDateTime? = null

    val coordinate: Optional<Coordinate>
        get() = if (this.latitude != null && this.longitude != null) {
            Optional.of(Coordinate(this.longitude, this.latitude))
        } else Optional.empty()
}
