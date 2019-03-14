package social.alone.server.event.type;

import social.alone.server.BaseControllerTest;
import social.alone.server.DisplayName;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.util.stream.IntStream;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EventTypeControllerTest extends BaseControllerTest {

  @Test
  @DisplayName("이벤트 타입을 조회")
  public void queryEvents__happy() throws Exception {
    // Given
    IntStream.range(0, 30).forEach(i -> this.createEventType("event category" + i));

    // When
    var perform = this.mockMvc.perform(
            get("/api/event-types")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
    );

    // Then
    perform
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("content[0].id").exists())
            .andExpect(jsonPath("content[0].value").exists())
            .andDo(
                    document("query-event-types"));
  }

}