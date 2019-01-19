package com.freestudy.api.event;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventTest {

  @Test
  public void builderTest() {
    Location location = new Location(
            "남부순환로 123",
            "스타벅스"
    );
    Event event = Event.builder()
            .name("스프링 부트 스터디 모임")
            .description("Original Gang Spring...을 정복해보자")
            .location(location)
            .build();
    assertThat(event).isNotNull();
  }

}
