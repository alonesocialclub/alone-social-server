package com.freestudy.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.freestudy.api.auth.SignUpRequestDto;
import com.freestudy.api.event.Event;
import com.freestudy.api.event.EventRepository;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "server.money-whip.com", uriPort = 443)
@Import(RestDocsConfiguration.class)
@ActiveProfiles("test")
@Ignore
public class BaseControllerTest {
  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected ObjectMapper objectMapper;

  @Autowired
  protected ModelMapper modelMapper;

  @Autowired
  protected  EventRepository eventRepository;

  private static final AtomicInteger count = new AtomicInteger(0);

  protected String getToken() throws Exception {
    var next = count.incrementAndGet();
    SignUpRequestDto data = SignUpRequestDto.builder()
            .email(next + "@test.com")
            .password("1234")
            .name("Jeff")
            .build();

    mockMvc.perform(
            post("/api/auth/signup")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaTypes.HAL_JSON)
                    .content(objectMapper.writeValueAsString(data))
    ).andExpect(status().isCreated());

    var result = mockMvc.perform(
            post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaTypes.HAL_JSON)
                    .content(objectMapper.writeValueAsString(data))
    );
    result.andExpect(status().isOk());

    String token = result.andReturn().getResponse().getContentAsString();
    return "Bearer " + token;
  }

  protected Event createEvent(int i) {
    Event event = Event.builder()
            .name("event" + i)
            .description("Rest")
            .startedAt(LocalDateTime.of(2018, 11, 11, 0, 0))
            .endedAt(LocalDateTime.of(2018, 11, 11, 0, 0))
            .limitOfEnrollment(5)
            .location("낙성대")
            .build();
    return this.eventRepository.save(event);
  }

}
