package com.freestudy.api.link;

import com.freestudy.api.BaseControllerTest;
import com.freestudy.api.event.Event;
import org.junit.Test;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LinkControllerTest extends BaseControllerTest {


  @Test
  public void getLink() throws Exception {
    // given
    Event event = createEvent();

    // when
    var perform = mockMvc.perform(
            post("/api/events/{id}/links", event.getId())
    );

    perform
            .andDo(print())
            .andDo(
                    document(
                            "create-link",
                            pathParameters(
                                    parameterWithName("id").description("event id")

                            ),
                            relaxedResponseFields(
                                    fieldWithPath("id").description("link id")
                            )
                    )
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").isNumber())
            .andExpect(jsonPath("url").isString())
            .andExpect(jsonPath("event.id").value(event.getId()));
  }
}