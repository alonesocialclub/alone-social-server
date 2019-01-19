package com.freestudy.api.event;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventTest {

  @Test
  public void builderTest() {
    Event event = Event.builder()
            .name("스프링 부트 스터디 모임")
            .description("Original Gang Spring...을 정복해보자")
            .build();
    assertThat(event).isNotNull();
  }

}
