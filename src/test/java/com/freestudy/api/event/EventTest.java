package com.freestudy.api.event;

import com.freestudy.api.location.Location;
import com.freestudy.api.user.User;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventTest {

  @Test
  public void builderTest() {
    User user = new User("test@test.com", "1234", "1234");
    Location location = new Location("남부순환로", "스타벅스", 123.123, 123.123, "https://naver.com");
    EventDto eventDto = EventDto.builder().location(location).build();
    Event event = new Event(eventDto, user);
    assertThat(event).isNotNull();
  }

}
