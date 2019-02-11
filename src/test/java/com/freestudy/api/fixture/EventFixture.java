package com.freestudy.api.fixture;


import com.freestudy.api.event.Event;
import com.freestudy.api.event.EventDto;
import com.freestudy.api.event.EventRepository;
import com.freestudy.api.event.location.Location;
import com.freestudy.api.user.User;

public class EventFixture {

  private EventRepository eventRepository;

  public EventFixture(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  public Event build() {
    User user = new User("email@email.com", "1234", "1234");
    Location location = new Location(
            "남부순환로 123",
            "스타벅스"
    );
    EventDto eventDto = EventDto.builder()
            .name("스프링 부트 스터디 모임")
            .description("Original Gang Spring...을 정복해보자")
            .location(location)
            .build();
    Event event = new Event(eventDto, user);
    return eventRepository.save(event);
  }

}
