package com.freestudy.api.event;

import com.freestudy.api.BaseControllerTest;
import com.freestudy.api.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EventControllerTest extends BaseControllerTest {

  @Test
  public void createEvent__happy() throws Exception {
    // Given
    EventDto event = EventDto.builder()
            .name("SpringBootIsFun")
            .description("Rest")
            .startedAt(LocalDateTime.of(2018, 11, 11, 12, 0))
            .endedAt(LocalDateTime.of(2018, 11, 11, 14, 0))
            .limitOfEnrollment(5)
            .location("낙성대")
            .build();

    // When
    var perfrom = mockMvc
            .perform(
                    post("/api/events/")
                            .header(HttpHeaders.AUTHORIZATION, getToken())
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .accept(MediaTypes.HAL_JSON)
                            .content(objectMapper.writeValueAsString(event))
            );

    // Then
    perfrom
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
                                    fieldWithPath("name").description("모임 이름"),
                                    fieldWithPath("description").description("모임 설명"),
                                    fieldWithPath("location").description("모임 장소"),
                                    fieldWithPath("startedAt").description("모임 시작 시간"),
                                    fieldWithPath("endedAt").description("모임 종료 시간"),
                                    fieldWithPath("limitOfEnrollment").description("모임 정원")
                            ),
                            relaxedResponseFields(
                                    fieldWithPath("id").description("event id")
                            )
                    )
            );

  }

  @Test
  @DisplayName("입력값이 없는 경우에")
  public void createEvent__empty_input() throws Exception {
    // Given
    EventDto eventDto = EventDto.builder().build();

    // When
    var perform = mockMvc
            .perform(
                    post("/api/events")
                            .header(HttpHeaders.AUTHORIZATION, getToken())
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .accept(MediaTypes.HAL_JSON)
                            .content(objectMapper.writeValueAsString(eventDto))
            );

    // Then
    perform
            .andDo(document("create-event-invalid"))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("_links.index").exists());
  }

  @Test
  @DisplayName("이벤트 시작일은 종료일보다 이전이여야 한다.")
  public void createEvent_invalid_input() throws Exception {
    // Given
    EventDto eventDto = EventDto.builder()
            .name("SpringBootIsFun")
            .description("Rest")
            .startedAt(LocalDateTime.of(2018, 11, 15, 0, 0))
            .endedAt(LocalDateTime.of(2018, 11, 11, 0, 0))
            .limitOfEnrollment(5)
            .location("낙성대")
            .build();

    // When
    var perform = mockMvc
            .perform(
                    post("/api/events")
                            .header(HttpHeaders.AUTHORIZATION, getToken())
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .accept(MediaTypes.HAL_JSON)
                            .content(objectMapper.writeValueAsString(eventDto))
            );

    // Then
    perform
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("content[0].objectName").exists())
            .andExpect(jsonPath("content[0].defaultMessage").exists())
            .andExpect(jsonPath("_links.index").exists());
  }


  @Test
  @DisplayName("30개의 이벤트를 페이징 조회")
  public void queryEvents__happy() throws Exception {
    // Given
    IntStream.range(0, 30).forEach(this::createEvent);

    // When
    var perform = this.mockMvc.perform(
            get("/api/events")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .param("page", "2")
                    .param("size", "5")
                    .param("sort", "name,desc")
    );

    // Then
    perform
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("page").exists())
            .andExpect(jsonPath("_embedded.eventList[0]._links.self").exists())
            .andExpect(jsonPath("_links.self").exists())
            .andExpect(jsonPath("_links.profile").exists())
            .andExpect(jsonPath("_links.create-event").doesNotExist())
            .andDo(
                    document("query-events",
                            requestParameters(
                                    parameterWithName("page").description("페이지"),
                                    parameterWithName("size").description("페이지의 크기"),
                                    parameterWithName("sort").description(":field,:sort 를 URL encoding 하여 보낸다.")
                            )
                    ));
  }

  @Test
  @DisplayName("인증정보가 있을 때 이벤트 생성 링크를 내려준다.")
  public void queryEventsWithAuth() throws Exception {
    // Given
    IntStream.range(0, 30).forEach(this::createEvent);

    // When
    var perform = this.mockMvc.perform(
            get("/api/events")
                    .header(HttpHeaders.AUTHORIZATION, getToken())
                    .param("page", "1")
                    .param("size", "10")
                    .param("sort", "name,desc")
    );

    // Then
    perform
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("page").exists())
            .andExpect(jsonPath("_embedded.eventList[0]._links.self").exists())
            .andExpect(jsonPath("_links.self").exists())
            .andExpect(jsonPath("_links.profile").exists())
            .andExpect(jsonPath("_links.create-event").exists())
            .andDo(document("query-events"));
  }

  @Test
  @DisplayName("기존 이벤트 하나 조회")
  public void getEvent() throws Exception {
    // Given
    Event event = this.createEvent(100);

    // When
    var perform = this.mockMvc.perform(get("/api/events/{id}", event.getId()));

    // Then
    perform.andExpect(status().isOk());
    perform.andExpect(jsonPath("id").exists());
    perform.andExpect(jsonPath("name").exists());
    perform.andExpect(jsonPath("description").exists());
    perform.andExpect(jsonPath("_links.self").exists());
    perform.andExpect(jsonPath("_links.profile").exists());
    perform.andDo(
            document("get-event",
                    pathParameters(
                            parameterWithName("id").description("event id")

                    )
            ));
  }


  @Test
  @DisplayName("기존 이벤트 하나 조회, 이벤트가 없을 때")
  public void getEvent__not_found() throws Exception {
    // When
    var perform = this.mockMvc.perform(get("/api/events/{id}", 0));

    // Then
    perform.andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("이벤트 수정")
  public void updateEvent__happy() throws Exception {
    // Given
    Event event = createEvent(200);
    EventDto eventDto = this.modelMapper.map(event, EventDto.class);
    String updatedName = "updated event";
    eventDto.setName(updatedName);

    // When
    var perform = this.mockMvc.perform(
            put("/api/events/{id}", event.getId())
                    .header(HttpHeaders.AUTHORIZATION, getToken())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(this.objectMapper.writeValueAsString(eventDto))
    );

    perform.andDo(print());
    perform.andDo(document("events-update"));
    perform.andExpect(status().isOk());
    perform.andExpect(jsonPath("name").value(updatedName));
  }

  @Test
  @DisplayName("이벤트 수정, 없는 이벤트에 대해서")
  public void updateEvent__not_found() throws Exception {
    // Given
    Event event = createEvent(123);
    EventDto eventDto = this.modelMapper.map(event, EventDto.class);

    // When
    var perform = this.mockMvc.perform(
            put("/api/events/0")
                    .header(HttpHeaders.AUTHORIZATION, getToken())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(this.objectMapper.writeValueAsString(eventDto))
    );

    perform.andDo(print());
    perform.andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("이벤트 수정, 시작시간을 종료시간 이후의 값을 넣는 경우")
  public void updateEvent__invalid_startedAt_endedAt() throws Exception {
    // Given
    Event event = createEvent(200);
    EventDto eventDto = this.modelMapper.map(event, EventDto.class);
    eventDto.setStartedAt(LocalDateTime.of(2018, 11, 16, 0, 0));
    eventDto.setEndedAt(LocalDateTime.of(2018, 11, 15, 0, 0));

    // When
    var perform = this.mockMvc.perform(
            put("/api/events/{id}", event.getId())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(this.objectMapper.writeValueAsString(eventDto))
                    .header(HttpHeaders.AUTHORIZATION, getToken())
    );

    // Then
    perform.andDo(print());
    perform.andExpect(status().isBadRequest());
  }

}
