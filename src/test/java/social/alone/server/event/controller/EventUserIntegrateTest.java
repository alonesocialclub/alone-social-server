package social.alone.server.event.controller;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import social.alone.server.BaseIntegrateTest;
import social.alone.server.event.domain.Event;

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
    ResultActions perform = this.mockMvc.perform(
            post("/api/events/{id}/users", event.getId())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .header(HttpHeaders.AUTHORIZATION, createUserAndBuildAuthToken())
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
    String token = createUserAndBuildAuthToken();
    // When
    this.mockMvc.perform(
            post("/api/events/{id}/users", event.getId())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .header(HttpHeaders.AUTHORIZATION, token)
    );
    ResultActions perform = this.mockMvc.perform(
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