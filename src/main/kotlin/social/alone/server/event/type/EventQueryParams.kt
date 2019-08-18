package social.alone.server.event.type

import java.time.LocalDateTime
import java.util.*

data class EventQueryParams (
        val longitude: Double? = null,
        val latitude: Double? = null
){

    val startedAt: LocalDateTime? = null

    val coordinate: Optional<Coordinate>
        get() = if (this.latitude != null && this.longitude != null) {
            Optional.of(Coordinate(this.longitude, this.latitude))
        } else Optional.empty()
}
