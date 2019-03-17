package social.alone.server.event;

import social.alone.server.event.repository.EventRepository;
import social.alone.server.location.Location;
import social.alone.server.user.UserRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@Ignore("TODO")
public class EventServiceTest {

  @InjectMocks
  EventService eventService;

  @Mock
  UserRepository userRepository;

  @Mock
  EventRepository eventRepository;

  @Test
  public void saveTest() {
    // Given
    when(userRepository).thenCallRealMethod();
    when(eventRepository).thenCallRealMethod();
    EventDto eventDto = EventDto.builder()
            .name("낙성대 주말 코딩")
            .description("오전 10시부터 오후 3시까지 각자 모여서 코딩합니다.")
            .startedAt(LocalDateTime.of(2018, 11, 11, 12, 0))
            .endedAt(LocalDateTime.of(2018, 11, 11, 14, 0))
            .limitOfEnrollment(5)
            .location(
                    new Location("남부순환로", "스타벅스", 123.123123, 123.123, "https://naver.com")
            )
            .build();

    // When
    Event event = eventService.create(eventDto, null);

    // Then
    assertThat(event.getName()).isEqualTo(eventDto.getName());
    assertThat(event.getCreatedAt()).isNotNull();
  }
}