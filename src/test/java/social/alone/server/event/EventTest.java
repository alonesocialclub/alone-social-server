//package social.alone.server.event;
//
//import org.junit.Test;
//import social.alone.server.event.domain.Event;
//import social.alone.server.event.dto.EventDto;
//import social.alone.server.location.LocationDto;
//import social.alone.server.user.domain.User;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class EventTest {
//
//  @Test
//  public void builderTest() {
//    User user = new User("test@test.com", "1234", "1234");
//    LocationDto location = new LocationDto("남부순환로", "스타벅스", 123.123, 123.123, "https://naver.com");
//    EventDto eventDto = EventDto.builder().location(location).build();
//    Event event = new Event(eventDto, user);
//    assertThat(event).isNotNull();
//  }
//
//}
