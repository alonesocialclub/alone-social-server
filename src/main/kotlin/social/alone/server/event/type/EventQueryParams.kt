package social.alone.server.event.type

import java.time.LocalDateTime
import java.util.*

data class EventQueryParams (
        val longitude: Double? = null,
        val latitude: Double? = null
){

    val startedAt: LocalDateTime? = null

    val coordinate: Coordinate?
        get() = if (this.latitude != null && this.longitude != null) {
            Coordinate(this.longitude, this.latitude)
        } else null
}
