package com.freestudy.api.event;

import com.freestudy.api.event.location.Location;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventServiceTest {

  @Autowired
  EventService eventService;

  @Test
  public void saveTest() {
    // Given
    EventDto eventDto = EventDto.builder()
            .name("낙성대 주말 코딩")
            .description("오전 10시부터 오후 3시까지 각자 모여서 코딩합니다.")
            .startedAt(LocalDateTime.of(2018, 11, 11, 12, 0))
            .endedAt(LocalDateTime.of(2018, 11, 11, 14, 0))
            .limitOfEnrollment(5)
            .location(new Location("남부순환로", "스타벅스"))
            .build();

    // When
    Event event = eventService.create(eventDto);

    // Then
    assertThat(event.getName()).isEqualTo(eventDto.getName());
    assertThat(event.getCreatedAt()).isNotNull();
  }
}