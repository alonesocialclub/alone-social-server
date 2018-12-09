package com.freestudy.api.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class EventControllerTest {


  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

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


    mockMvc
            .perform(
                    post("/api/events/")
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .accept(MediaTypes.HAL_JSON)
                            .content(objectMapper.writeValueAsString(event))
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("id").exists());
  }

}
