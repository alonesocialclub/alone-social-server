package com.freestudy.api.auth;

import com.freestudy.api.BaseControllerTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends BaseControllerTest {

  @Test
  public void signupTest() throws Exception {
    // Given
    SignUpRequestDto data = SignUpRequestDto.builder()
            .email("signupTest@test.com")
            .password("1234")
            .name("Jeff")
            .build();

    // When
    var result = mockMvc.perform(
            post("/api/auth/signup")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaTypes.HAL_JSON)
                    .content(objectMapper.writeValueAsString(data))
    );

    // Then
    result
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("auth-signup"));
  }

  @Test
  public void loginTest() throws Exception {
    SignUpRequestDto data = SignUpRequestDto.builder()
            .email("loginTest@test.com")
            .password("1234")
            .name("Jeff")
            .build();

    mockMvc.perform(
            post("/api/auth/signup")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaTypes.HAL_JSON)
                    .content(objectMapper.writeValueAsString(data))
    ).andExpect(status().isCreated());

    // When
    var result = mockMvc.perform(
            post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaTypes.HAL_JSON)
                    .content(objectMapper.writeValueAsString(data))
    );

    // Then
    result.andExpect(status().isOk());
    result.andDo(print());
    String token = result.andReturn().getResponse().getContentAsString();
    Assert.assertNotNull(token);
  }

}