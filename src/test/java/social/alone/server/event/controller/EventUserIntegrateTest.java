package social.alone.server.event.controller;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import social.alone.server.BaseIntegrateTest;
import social.alone.server.event.Event;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EventUserIntegrateTest extends BaseIntegrateTest {

  @Test
  public void eventJoin() throws Exception {
    // Given
    Event event = createEvent();
    // When
    var perform = this.mockMvc.perform(
            post("/api/events/{id}/users", event.getId())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .header(HttpHeaders.AUTHORIZATION, buildAuthToken())
    );

    // Then
    perform.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.users.length()").value(1));

    perform.andDo(document("events-users-update"));
  }

  @Test
  public void eventJoinCancel() throws Exception {
    // Given
    Event event = createEvent();
    var token = buildAuthToken();
    // When
    this.mockMvc.perform(
            post("/api/events/{id}/users", event.getId())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .header(HttpHeaders.AUTHORIZATION, token)
    );
    var perform = this.mockMvc.perform(
            delete("/api/events/{id}/users", event.getId())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .header(HttpHeaders.AUTHORIZATION, token)
    );

    // Then
    perform.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.users.length()").value(0));

    perform.andDo(document("events-users-update-cancel"));
  }

}