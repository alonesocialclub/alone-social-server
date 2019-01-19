package com.freestudy.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.freestudy.api.auth.SignUpRequestDto;
import com.freestudy.api.event.Event;
import com.freestudy.api.event.EventRepository;
import com.freestudy.api.link.Link;
import com.freestudy.api.link.LinkRepository;
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
  protected LinkRepository linkRepository;


  protected String getAuthToken() throws Exception {

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

    var next = atomicInteger.incrementAndGet();

    Event event = Event.builder()
            .name("event" + next)
            .description("Rest")
            .startedAt(LocalDateTime.of(2018, 11, 11, 0, 0))
            .endedAt(LocalDateTime.of(2018, 11, 11, 0, 0))
            .limitOfEnrollment(5)
            .location("낙성대")
            .build();

    return this.eventRepository.save(event);
  }

  protected Link createLink() {
    Event event = createEvent();

    Link link = Link.builder()
            .event(event)
            .build();

    return this.linkRepository.save(link);
  }

}
