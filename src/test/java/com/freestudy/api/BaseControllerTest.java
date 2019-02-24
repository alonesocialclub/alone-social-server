package com.freestudy.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.freestudy.api.auth.SignUpRequestDto;
import com.freestudy.api.event.Event;
import com.freestudy.api.event.EventDto;
import com.freestudy.api.event.EventRepository;
import com.freestudy.api.event.location.Location;
import com.freestudy.api.event.type.EventType;
import com.freestudy.api.event.type.EventTypeRepository;
import com.freestudy.api.link.Link;
import com.freestudy.api.link.LinkRepository;
import com.freestudy.api.user.User;
import com.freestudy.api.user.UserRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "server.money-whip.com", uriPort = 443)
@Import(RestDocsConfiguration.class)
@ActiveProfiles("test")
@Ignore
public class BaseControllerTest {

  private static final AtomicInteger atomicInteger = new AtomicInteger(0);

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected ObjectMapper objectMapper;

  @Autowired
  protected ModelMapper modelMapper;

  @Autowired
  protected EventRepository eventRepository;

  @Autowired
  protected UserRepository userRepository;

  @Autowired
  protected EventTypeRepository eventTypeRepository;

  @Autowired
  protected LinkRepository linkRepository;

  protected User createUser() {
    var next = atomicInteger.incrementAndGet();
    User user = new User("foo" + next + "@test.com", "1234", "local");
    return this.userRepository.save(user);
  }


  protected String buildAuthToken() throws Exception {

    var next = atomicInteger.incrementAndGet();

    SignUpRequestDto data = SignUpRequestDto.builder()
            .email(next + "@test.com")
            .password("12345678")
            .name("Jeff")
            .build();

    var perform = mockMvc.perform(
            post("/api/auth/signup")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(data))
    ).andExpect(status().isCreated());

    String response = perform.andReturn().getResponse().getContentAsString();
    String token = JsonPath.parse(response).read("token").toString();

    return "Bearer " + token;
  }

  protected Event createEvent() {
    return createEvent(LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(6));
  }

  protected Event createEvent(LocalDateTime startedAt, LocalDateTime endedAt){
    Location location = new Location(
            "서울 서초구 강남대로61길 3",
            "스타벅스",
            127.026503385182,
            37.4991561765984,
            "http://place.map.daum.net/27290899");
    User user = this.createUser();
    var next = atomicInteger.incrementAndGet();
    EventDto eventDto = EventDto.builder()
            .name("event" + next)
            .description("Rest")
            .startedAt(startedAt)
            .endedAt(endedAt)
            .limitOfEnrollment(5)
            .location(location)
            .build();
    Event event = new Event(eventDto, user);
    return this.eventRepository.save(event);
  }

  protected Link createLink() {
    Event event = createEvent();
    Link link = event.createLink();
    return this.linkRepository.save(link);
  }

  protected EventType createEventType(String value) {
    EventType eventType = EventType.of(value);
    return eventTypeRepository.save(eventType);
  }
}
