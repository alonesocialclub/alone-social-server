package com.freestudy.api.config;

import com.freestudy.api.event.Event;
import com.freestudy.api.event.EventDto;
import com.freestudy.api.event.Location;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppConfigTest {

  @Autowired
  private ModelMapper modelMapper;

  @Test
  public void modelMapperTest() {
    EventDto eventDto = EventDto.builder()
            .name("낙성대 주말 코딩")
            .description("오전 10시부터 오후 3시까지 각자 모여서 코딩합니다.")
            .startedAt(LocalDateTime.of(2018, 11, 11, 12, 0))
            .endedAt(LocalDateTime.of(2018, 11, 11, 14, 0))
            .limitOfEnrollment(5)
            .location(new Location("남부순환로", "스타벅스"))
            .build();

    Event event = modelMapper.map(eventDto, Event.class);

    assertThat(event.getName()).isEqualTo(eventDto.getName());
  }

}