package social.alone.server.event.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import social.alone.server.BaseControllerTest;
import social.alone.server.DisplayName;
import social.alone.server.event.Event;

import java.util.stream.IntStream;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EventSearchControllerTest extends BaseControllerTest {

  @Test
  @DisplayName("30개의 이벤트를 페이징 조회")
  public void queryEvents__happy() throws Exception {
    // Given
    IntStream.range(0, 10).forEach(__ -> this.createEvent());

    // When
    var perform = this.mockMvc.perform(
            get("/api/events")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .param("page", "0")
                    .param("size", "2")
                    .param("sort", "name,desc")
    );

    // Then
    perform
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("content[0].id").exists())
            .andDo(
                    document("query-events",
                            requestParameters(
                                    parameterWithName("page").description("페이지"),
                                    parameterWithName("size").description("페이지의 크기"),
                                    parameterWithName("sort").description("<:field>,<:sort> 형태. 값을 URL encoding 해야한다. 예시 참고")
                            )
                    ));
  }

  @Test
  @DisplayName("내가 만든 이벤트")
  public void queryEvents__type_owner() throws Exception {
    // Given
    IntStream.range(0, 10).forEach(__ -> this.createEvent());

    // When
    var perform = this.mockMvc.perform(
            get("/api/events")
                    .param("type", "OWNER")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
    );

    // Then
    perform
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("content.length()").value(10));
  }

  @Test
  @DisplayName("좌표 기반 쿼리")
  public void queryEvents__type_location() throws Exception {
    // Given
    IntStream.range(0, 10).forEach(__ -> this.createEvent());

    // When
    var perform = this.mockMvc.perform(
            get("/api/events")
                    .param("longitude", "37.503951")
                    .param("latitude", "127.046842")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
    );

    // Then
    perform
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("content.length()").value(10));
  }

  @Test
  @DisplayName("기존 이벤트 하나 조회")
  public void getEvent() throws Exception {
    // Given
    Event event = createEvent();

    // When
    var perform = this.mockMvc.perform(get("/api/events/{id}", event.getId()));

    // Then
    perform.andExpect(status().isOk());
    perform.andExpect(jsonPath("id").exists());
    perform.andExpect(jsonPath("name").exists());
    perform.andExpect(jsonPath("description").exists());
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
}