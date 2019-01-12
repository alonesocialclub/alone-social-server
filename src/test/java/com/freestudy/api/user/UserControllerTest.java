package com.freestudy.api.user;

import com.freestudy.api.BaseControllerTest;
import org.junit.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerTest extends BaseControllerTest {

  @Test
  public void getUsersMeTest() throws Exception {
    // Given
    var token = getToken();

    // When
    var perform = mockMvc
            .perform(
                    get("/api/users/me")
                            .header(HttpHeaders.AUTHORIZATION, token)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .accept(MediaTypes.HAL_JSON)
            );

    // Then
    perform
            .andDo(print())
            .andDo(
                    document("get-user-me")
            )
            .andExpect(status().isOk());
  }

  @Test
  public void getUsersMeTest__without_token() throws Exception {
    // Given & When
    var perform = mockMvc
            .perform(
                    get("/api/users/me")
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .accept(MediaTypes.HAL_JSON)
            );

    // Then
    perform.andExpect(status().isUnauthorized())
            .andDo(print());
  }

}