package social.alone.server


import social.alone.server.event.domain.Event
import social.alone.server.event.dto.EventDto
import social.alone.server.event.repository.EventRepository
import social.alone.server.event.type.EventTypeDto
import social.alone.server.location.Location
import social.alone.server.location.LocationDto
import java.time.LocalDateTime
import java.util.*

class EventFixture(private val eventRepository: EventRepository) {

    fun build(): Event {
        // Given
        val eventTypes = HashSet<EventTypeDto>()
        val eventDto = EventDto(
                "낙성대 주말 코딩",
                LocationDto(
                        "서울 서초구 강남대로61길 3",
                        "스타벅스",
                        127.026503385182,
                        37.4991561765984,
                        "http://place.map.daum.net/27290899"),
                LocalDateTime.of(2018, 11, 11, 12, 0),
                LocalDateTime.of(2018, 11, 11, 14, 0),
                eventTypes
        )
        val user = makeUser()
        val location = Location("남부순환로", "스타벅스", 123.123, 123.123, "https://naver.com")
        val event = Event(eventDto, user)
        event.updateLocation(location)
        return eventRepository.save(event)
    }

}
