package com.freestudy.api.fixture;


import com.freestudy.api.event.Event;
import com.freestudy.api.event.EventRepository;
import com.freestudy.api.event.location.Location;

public class EventFixture {

  private EventRepository eventRepository;

  public EventFixture(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  public Event build() {
    Location location = new Location(
            "남부순환로 123",
            "스타벅스"
    );
    Event event = Event.builder()
            .name("스프링 부트 스터디 모임")
            .description("Original Gang Spring...을 정복해보자")
            .location(location)
            .build();
    return eventRepository.save(event);
  }

}
