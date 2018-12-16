package com.freestudy.api.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freestudy.api.DisplayName;
import com.freestudy.api.RestDocsConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
@ActiveProfiles("test")
public class EventControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  @DisplayName("정상적으로 이벤트를 사용할 때")
  public void createEvent() throws Exception {

    EventDto event = EventDto.builder()
            .name("SpringBootIsFun")
            .description("Rest")
            .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 11, 0, 0))
            .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 11, 0, 0))
            .beginEventDateTime(LocalDateTime.of(2018, 11, 11, 0, 0))
            .endEventDateTime(LocalDateTime.of(2018, 11, 11, 0, 0))
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
            .andExpect(jsonPath("id").isNumber())
            .andExpect(header().exists(HttpHeaders.LOCATION))
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
            .andDo(
                    document("create-event",
                            links(
                                    linkWithRel("self").description("link to self"),
                                    linkWithRel("query-events").description("query")
                            ),
                            requestHeaders(
                                    headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                    headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                            ),
                            requestFields(
                                    fieldWithPath("name").description("Name of new event"),
                                    fieldWithPath("description").description("Description of new event"),
                                    fieldWithPath("location").description("Location of new event"),
                                    fieldWithPath("beginEnrollmentDateTime").description("Description of new event"),
                                    fieldWithPath("closeEnrollmentDateTime").description("모임 등록 마감 시간"),
                                    fieldWithPath("beginEventDateTime").description("beginEventDateTime"),
                                    fieldWithPath("endEventDateTime").description("endEventDateTime"),
                                    fieldWithPath("basePrice").description("basePrice"),
                                    fieldWithPath("maxPrice").description("maxPrice"),
                                    fieldWithPath("limitOfEnrollment").description("limitOfEnrollment")
                            ),
                            relaxedResponseFields(
                                    fieldWithPath("id").description("event id")
                            )
                    )
            );

  }

  @Test
  @DisplayName("입력값이 없는 경우에")
  public void createEvent_Bad_Request_Empty_Input() throws Exception {

    EventDto eventDto = EventDto.builder().build();

    mockMvc
            .perform(
                    post("/api/events/")
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .accept(MediaTypes.HAL_JSON)
                            .content(objectMapper.writeValueAsString(eventDto))
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("_links.index").exists());
  }

  @Test
  @DisplayName("입력값이 있으나 잘못되 경우일 때, 이벤트 시작일은 종료일보다 이전이여야 한다.")
  public void createEvent_Bad_Request_Invalid_Input() throws Exception {

    EventDto eventDto = EventDto.builder()
            .name("SpringBootIsFun")
            .description("Rest")
            .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 11, 0, 0))
            .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 11, 0, 0))
            .beginEventDateTime(LocalDateTime.of(2018, 11, 15, 0, 0))
            .endEventDateTime(LocalDateTime.of(2018, 11, 10, 0, 0))
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
                            .content(objectMapper.writeValueAsString(eventDto))
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("_links.index").exists());
  }

  @Test
  @DisplayName("입력값이 있으나 잘못되 경우일 때, 이에 대한 정보가 응답에 담겨야한다.")
  public void createEvent_Bad_Request_Invalid_Input_With_Response() throws Exception {

    EventDto eventDto = EventDto.builder()
            .name("SpringBootIsFun")
            .description("Rest")
            .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 11, 0, 0))
            .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 11, 0, 0))
            .beginEventDateTime(LocalDateTime.of(2018, 11, 15, 0, 0))
            .endEventDateTime(LocalDateTime.of(2018, 11, 10, 0, 0))
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
                            .content(objectMapper.writeValueAsString(eventDto))
            )
            .andDo(print())
            .andExpect(jsonPath("content[0].objectName").exists())
            .andExpect(jsonPath("content[0].defaultMessage").exists())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("_links.index").exists());
  }


}
