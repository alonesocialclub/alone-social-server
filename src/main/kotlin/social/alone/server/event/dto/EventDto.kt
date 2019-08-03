package social.alone.server.event.dto


import social.alone.server.event.type.EventTypeDto
import social.alone.server.location.Location
import social.alone.server.location.LocationDto
import java.time.LocalDateTime
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull


data class EventDto (
    @NotEmpty
    var name: String,

    @NotEmpty
    var description: String,

    var location: LocationDto,

    var startedAt: LocalDateTime,

    @NotNull
    var endedAt: LocalDateTime,

    var limitOfEnrollment: Int = 0,

    var eventTypes: Set<EventTypeDto> = HashSet()
){

    fun getLocation(): Location {
        return location.buildLocation()
    }


}

