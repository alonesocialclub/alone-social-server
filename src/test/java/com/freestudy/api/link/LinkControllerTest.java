package com.freestudy.api.link;

import com.freestudy.api.BaseControllerTest;
import com.freestudy.api.event.Event;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LinkControllerTest extends BaseControllerTest {

  @Test
  public void createLinkTest() throws Exception {
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

  @Test
  public void createLinkTest__not_found() throws Exception {

    // when
    var perform = mockMvc.perform(
            post("/api/events/{id}/links", -1)
    );

    perform
            .andExpect(status().isNotFound());
  }

  @Test
  public void getLinkTest() throws Exception {
    // given
    Link link = createLink();

    // when
    var perform = mockMvc.perform(
            get(
                    "/api/events/{eventId}/links", link.getEvent().getId()
            ).contentType(
                    MediaType.TEXT_HTML
            )
    );

    perform
            .andDo(print())
            .andDo(
                    document(
                            "get-link",
                            pathParameters(
                                    parameterWithName("eventId").description("event id")
                            )
                    )
            )
            .andExpect(status().isOk());

  }
}