package social.alone.server.event.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import social.alone.server.BaseControllerTest;
import social.alone.server.DisplayName;
import social.alone.server.event.Event;
import social.alone.server.event.EventDto;
import social.alone.server.event.type.EventType;
import social.alone.server.event.type.EventTypeDto;
import social.alone.server.location.Location;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EventControllerTest extends BaseControllerTest {

  private Location location;

  @Before
  public void setUp() {
    location = new Location(
            "서울 서초구 강남대로61길 3",
            "스타벅스",
            127.026503385182,
            37.4991561765984,
            "http://place.map.daum.net/27290899");
  }

  @Test
  public void createEventTest() throws Exception {
    // Given
    EventType eventType1 = createEventType("밥 같이 먹어요");
    EventType eventType2 = createEventType("조금 떠들어요");
    Set<EventTypeDto> eventTypes = new HashSet<>(Arrays.asList(eventType1.toDto(), eventType2.toDto()));
    EventDto event = EventDto.builder()
            .name("낙성대 주말 코딩")
            .description("오전 10시부터 오후 3시까지 각자 모여서 코딩합니다.")
            .startedAt(LocalDateTime.of(2018, 11, 11, 12, 0))
            .endedAt(LocalDateTime.of(2018, 11, 11, 14, 0))
            .limitOfEnrollment(5)
            .location(location)
            .eventTypes(eventTypes)
            .build();

    // When
    var perform = mockMvc
            .perform(
                    post("/api/events/")
                            .header(HttpHeaders.AUTHORIZATION, buildAuthToken())
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .accept(MediaType.APPLICATION_JSON_UTF8)
                            .content(objectMapper.writeValueAsString(event))
            );

    // Then
    perform
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").isNumber())
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("location.imageUrl").isNotEmpty())
            .andDo(
                    document("post-events",
                            requestHeaders(
                                    headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                    headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                            ),
                            relaxedRequestFields(
                                    fieldWithPath("name").description("모임 이름"),
                                    fieldWithPath("description").description("모임 설명"),
                                    fieldWithPath("location.address").description("모임 장소 주소"),
                                    fieldWithPath("location.name").description("모임 장소 이름"),
                                    fieldWithPath("location.placeUrl").description("모임 장소 url"),
                                    fieldWithPath("location.latitude").description("모임 장소 latitude"),
                                    fieldWithPath("location.longitude").description("모임 장소 longitude"),
                                    fieldWithPath("location.imageUrl").description("모임 장소 imageUrl"),
                                    fieldWithPath("eventTypes[].id").description("모임 성격 id"),
                                    fieldWithPath("eventTypes[].value").description("모임 성격 값"),
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
  public void createEventTest__empty_input() throws Exception {
    // Given
    EventDto eventDto = EventDto.builder().build();

    // When
    var perform = mockMvc
            .perform(
                    post("/api/events")
                            .header(HttpHeaders.AUTHORIZATION, buildAuthToken())
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(objectMapper.writeValueAsString(eventDto))
            );

    // Then
    perform
            .andDo(document("post-events-invalid"))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("_links.index").exists());
  }

  @Test
  @DisplayName("이벤트 시작일은 종료일보다 이전이여야 한다.")
  public void createEventTest_invalid_input() throws Exception {
    // Given
    EventDto eventDto = EventDto.builder()
            .name("SpringBootIsFun")
            .description("Rest")
            .startedAt(LocalDateTime.of(2018, 11, 15, 0, 0))
            .endedAt(LocalDateTime.of(2018, 11, 11, 0, 0))
            .limitOfEnrollment(5)
            .location(location)
            .build();

    // When
    var perform = mockMvc
            .perform(
                    post("/api/events")
                            .header(HttpHeaders.AUTHORIZATION, buildAuthToken())
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
  @DisplayName("이벤트 수정")
  public void updateEvent__happy() throws Exception {
    // Given
    Event event = createEvent();
    String updatedName = "updated event";
    EventDto eventDto = EventDto
            .builder()
            .name(updatedName)
            .location(location)
            .description(event.getDescription())
            .startedAt(event.getStartedAt())
            .endedAt(event.getEndedAt())
            .limitOfEnrollment(event.getLimitOfEnrollment())
            .build();

    // When
    var perform = this.mockMvc.perform(
            put("/api/events/{id}", event.getId())
                    .header(HttpHeaders.AUTHORIZATION, buildAuthToken())
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
    Event event = createEvent();
    EventDto eventDto = this.modelMapper.map(event, EventDto.class);
    int eventIdNotExists = -1;

    // When
    var perform = this.mockMvc.perform(
            put("/api/events/{id}", eventIdNotExists)
                    .header(HttpHeaders.AUTHORIZATION, buildAuthToken())
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
    Event event = createEvent();
    EventDto eventDto = EventDto.builder()
            .startedAt(LocalDateTime.of(2018, 11, 16, 0, 0))
            .endedAt(LocalDateTime.of(2018, 11, 15, 0, 0))
            .build();

    // When
    var perform = this.mockMvc.perform(
            put("/api/events/{id}", event.getId())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(this.objectMapper.writeValueAsString(eventDto))
                    .header(HttpHeaders.AUTHORIZATION, buildAuthToken())
    );

    // Then
    perform.andDo(print());
    perform.andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("이벤트 삭제")
  public void deleteEvent() throws Exception {
    // Given
    Event event = createEvent();

    // When
    var perform = this.mockMvc.perform(
            delete("/api/events/{id}", event.getId())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .header(HttpHeaders.AUTHORIZATION, buildAuthToken())
    );

    // Then
    perform.andDo(print());
    perform.andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("이벤트에 장소가 중복으로 insert 되지 않아야 한다")
  public void eventLocationDuplicated() throws Exception {
    // Given
    var startedAt = LocalDateTime.now().plusDays(3);
    var endedAt = LocalDateTime.now().plusDays(6);

    EventDto event1 = EventDto.builder()
            .name("낙성대 주말 코딩1")
            .description("오전 10시부터 오후 3시까지 각자 모여서 코딩합니다.")
            .startedAt(startedAt)
            .endedAt(endedAt)
            .limitOfEnrollment(5)
            .location(location)
            .build();
    EventDto event2 = EventDto.builder()
            .name("낙성대 주말 코딩2")
            .description("오전 10시부터 오후 3시까지 각자 모여서 코딩합니다.")
            .startedAt(startedAt)
            .endedAt(endedAt)
            .limitOfEnrollment(5)
            .location(location)
            .build();

    // When
    mockMvc
            .perform(
                    post("/api/events/")
                            .header(HttpHeaders.AUTHORIZATION, buildAuthToken())
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .accept(MediaType.APPLICATION_JSON_UTF8)
                            .content(objectMapper.writeValueAsString(event1))
            );

    // When
    mockMvc
            .perform(
                    post("/api/events/")
                            .header(HttpHeaders.AUTHORIZATION, buildAuthToken())
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .accept(MediaType.APPLICATION_JSON_UTF8)
                            .content(objectMapper.writeValueAsString(event2))
            );

    var perform = this.mockMvc.perform(
            get("/api/events")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
    );

    // Then
    perform.andDo(print())
            .andExpect(status().isOk());

    var json = perform.andReturn().getResponse().getContentAsString();
    String a = JsonPath.parse(json).read("$.content[0].location.id").toString();
    String b = JsonPath.parse(json).read("$.content[1].location.id").toString();
    assertThat(a).isEqualTo(b);
  }

}
