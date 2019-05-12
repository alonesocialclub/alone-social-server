package social.alone.server.fixture;


import social.alone.server.event.Event;
import social.alone.server.event.dto.EventDto;
import social.alone.server.event.repository.EventRepository;
import social.alone.server.location.Location;
import social.alone.server.user.User;

public class EventFixture {

  private EventRepository eventRepository;

  public EventFixture(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  public Event build() {
    User user = new User("email@email.com", "1234", "1234");
    Location location = new Location("남부순환로", "스타벅스", 123.123, 123.123, "https://naver.com");
    EventDto eventDto = EventDto.builder()
            .name("스프링 부트 스터디 모임")
            .description("Original Gang Spring...을 정복해보자")
            .build();
    Event event = new Event(eventDto, user);
    event.updateLocation(location);
    return eventRepository.save(event);
  }

}
