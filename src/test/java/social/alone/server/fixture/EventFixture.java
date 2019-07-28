package social.alone.server.fixture;


import social.alone.server.event.domain.Event;
import social.alone.server.event.dto.EventDto;
import social.alone.server.event.repository.EventRepository;
import social.alone.server.event.type.EventType;
import social.alone.server.event.type.EventTypeDto;
import social.alone.server.location.Location;
import social.alone.server.location.LocationDto;
import social.alone.server.user.domain.User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EventFixture {

  private EventRepository eventRepository;

  public EventFixture(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  public Event build() {
    // Given
    Set<EventTypeDto> eventTypes = new HashSet<>();
    EventDto eventDto = new EventDto(
            "낙성대 주말 코딩",
            "오전 10시부터 오후 3시까지 각자 모여서 코딩합니다.",
            new LocationDto(
                    "서울 서초구 강남대로61길 3",
                    "스타벅스",
                    127.026503385182,
                    37.4991561765984,
                    "http://place.map.daum.net/27290899"),
            LocalDateTime.of(2018, 11, 11, 12, 0),
            LocalDateTime.of(2018, 11, 11, 14, 0),
            5,
            eventTypes
    );
    User user = new User("email@email.com", "1234", "1234");
    Location location = new Location("남부순환로", "스타벅스", 123.123, 123.123, "https://naver.com");
    Event event = new Event(eventDto, user);
    event.updateLocation(location);
    return eventRepository.save(event);
  }

}
