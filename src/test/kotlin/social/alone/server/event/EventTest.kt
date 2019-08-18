package social.alone.server.event

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import social.alone.server.event.domain.Event
import social.alone.server.event.dto.EventDto
import social.alone.server.location.LocationDto
import social.alone.server.user.domain.User
import java.time.LocalDateTime


class EventTest {

    @Test
    fun builderTest() {
        val user = User("test@test.com", "1234", "1234")
        val location = LocationDto("남부순환로", "스타벅스", 123.123, 123.123, "https://naver.com")
        val eventDto = EventDto(
                "name", "description", location, LocalDateTime.now(), LocalDateTime.now().plusHours(3)
        )
        val event = Event(eventDto, user)
        assertThat(event).isNotNull
    }

}
