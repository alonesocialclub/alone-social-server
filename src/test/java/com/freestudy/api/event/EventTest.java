package com.freestudy.api.event;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventTest {

  @Test
  public void builder() {
    Event event = Event.builder().build();
    assertThat(event).isNotNull();
  }
}