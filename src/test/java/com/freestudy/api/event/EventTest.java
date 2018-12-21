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

  @Test
  public void javaBeanTest() {
    Event event = new Event();
    event.setId(1);
    event.setName("각자 모여서 코딩합시다");
    event.setDescription("노트북 두고 화장실은 가야하니까.");
    assertThat(event).isNotNull();
  }

}
