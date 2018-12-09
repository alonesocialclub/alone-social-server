package com.freestudy.api.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class EventControllerTest {


  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  EventRepository eventRepository;

  @Test
  public void createEvent() throws Exception {

    Event event = Event.builder()
            .name("SpringBootIsFun")
            .description("Rest")
            .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 11, 0, 0))
            .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 11, 0, 0))
            .beginEventDatetime(LocalDateTime.of(2018, 11, 11, 0, 0))
            .endEventDatetime(LocalDateTime.of(2018, 11, 11, 0, 0))
            .basePrice(1000)
            .maxPrice(10000)
            .limitOfEnrollment(5)
            .location("낙성대")
            .build();

    event.setId(10);
    Mockito.when(eventRepository.save(event)).thenReturn(event);

    mockMvc
            .perform(
                    post("/api/events/")
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .accept(MediaTypes.HAL_JSON)
                            .content(objectMapper.writeValueAsString(event))
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("id").exists())
            .andExpect(header().exists(HttpHeaders.LOCATION))
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE));

  }

}
