package social.alone.server.event.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import social.alone.server.RepositoryTest;
import social.alone.server.event.Event;
import social.alone.server.event.EventDto;
import social.alone.server.location.Location;
import social.alone.server.user.User;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class EventRepositoryImplTest extends RepositoryTest {
  @Autowired
  EventRepository eventRepository;

  @Test
  public void test() {
    User user = new User("test@test.com", "1234", "1234");
    Location location = new Location("남부순환로", "스타벅스", 123.123, 123.123, "https://naver.com");
    EventDto eventDto = EventDto.builder()
            .name("foo")
            .description("bar")
            .endedAt(LocalDateTime.now())
            .startedAt(LocalDateTime.now())
            .build();
    Event event = new Event(eventDto, user);
    event.updateLocation(location);

    Event saved = eventRepository.save(event);

    assertThat(saved.getCreatedAt()).isNotNull();
    assertThat(saved.getUpdatedAt()).isNotNull();
    assertThat(saved.getId()).isNotNull();
  }
}