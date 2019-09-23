package social.alone.server.event.dto


import social.alone.server.event.type.EventTypeDto
import social.alone.server.location.Location
import social.alone.server.location.LocationDto
import java.time.LocalDateTime
import javax.validation.constraints.NotEmpty


data class EventDto (

    @NotEmpty
    val description: String,

    val location: LocationDto,

    val startedAt: LocalDateTime,

    val endedAt: LocalDateTime? = null,

    var eventTypes: Set<EventTypeDto> = HashSet()
){

    fun getLocation(): Location {
        return location.buildLocation()
    }

}

