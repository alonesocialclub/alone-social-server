package social.alone.server.user.controller;


import org.junit.Test;
import org.springframework.http.MediaType;
import social.alone.server.BaseControllerTest;
import social.alone.server.event.Event;

import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserEventMutationControllerTest extends BaseControllerTest {

  @Test
  public void queryEventsTest() throws Exception {
    List<Event> eventList = Arrays.asList(this.createEvent(), this.createEvent());

    var perform = this.mockMvc.perform(
            get("/api/users/{id}/events", eventList.get(0).getOwner().getId())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .param("type", "OWNER")
                    .param("page", "0")
                    .param("size", "2")
                    .param("sort", "name,desc")
    );

    perform.andDo(print());
    perform.andExpect(status().isOk());
    perform.andDo(
            document("get-events-by-user",
                    requestParameters(
                            parameterWithName("page").description("페이지"),
                            parameterWithName("size").description("페이지의 크기"),
                            parameterWithName("sort").description("<:field>,<:sort> 형태. 값을 URL encoding 해야한다. 예시 참고"),
                            parameterWithName("type").description("ALL,OWNER,JOINER")
                    )
                    )
    );
  }


}