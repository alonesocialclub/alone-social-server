package com.freestudy.api.user;

import com.freestudy.api.BaseControllerTest;
import com.freestudy.api.interest.InterestDto;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerTest extends BaseControllerTest {

  @Test
  public void getUsersMeTest() throws Exception {
    // Given
    var token = getAuthToken();

    // When
    var perform = mockMvc
            .perform(
                    get("/api/users/me")
                            .header(HttpHeaders.AUTHORIZATION, token)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .accept(MediaType.APPLICATION_JSON_UTF8)
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
                            .accept(MediaType.APPLICATION_JSON_UTF8)
            );

    // Then
    perform.andExpect(status().isUnauthorized())
            .andDo(print());
  }

  @Test
  public void putUsersMeTest() throws Exception {
    // Given
    var token = getAuthToken();

    List<InterestDto> interestDtoList = Arrays.asList(
            InterestDto.builder().value("서예").build(),
            InterestDto.builder().value("스타트업").build()
    );

    UserDto userDto = UserDto.builder()
            .name("foo")
            .email("putUserMeTest@email.com")
            .interests(interestDtoList)
            .build();

    // When
    var perform = mockMvc
            .perform(
                    put("/api/users/me")
                            .header(HttpHeaders.AUTHORIZATION, token)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(this.objectMapper.writeValueAsString(userDto))
                            .accept(MediaType.APPLICATION_JSON_UTF8)
            );

    // Then
    perform
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("name").value(userDto.getName()))
            .andExpect(jsonPath("email").value(userDto.getEmail()))
            .andExpect(jsonPath("interests[0].value").value(interestDtoList.get(0).getValue()))
            .andExpect(jsonPath("interests[1].value").value(interestDtoList.get(1).getValue()));


    perform
            .andDo(
                    document(
                            "post-users-me"
                    )
            );
  }

  @Test
  public void putUsersMeTest__too_much_interests() throws Exception {
    // Given
    var token = getAuthToken();
    List<InterestDto> interestDtoList = Arrays.asList(
            InterestDto.builder().value("사후세계").build(),
            InterestDto.builder().value("스타트업").build(),
            InterestDto.builder().value("배구").build(),
            InterestDto.builder().value("그림").build(),
            InterestDto.builder().value("음악").build(),
            InterestDto.builder().value("역사").build()
    );

    UserDto userDto = UserDto.builder()
            .name("foo")
            .email("putUserMeTest@email.com")
            .interests(interestDtoList)
            .build();

    // When
    var perform = mockMvc
            .perform(
                    put("/api/users/me")
                            .header(HttpHeaders.AUTHORIZATION, token)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(this.objectMapper.writeValueAsString(userDto))
                            .accept(MediaType.APPLICATION_JSON_UTF8)
            );

    // Then
    perform
            .andDo(print())
            .andExpect(status().isBadRequest());
  }
  @Test
  public void putUsersMeTest__invalid_email_format() throws Exception {
    // Given
    var token = getAuthToken();
    UserDto userDto = UserDto.builder()
            .name("foo")
            .email("invalid.email.com")
            .build();

    // When
    var perform = mockMvc

            .perform(
                    put("/api/users/me")
                            .header(HttpHeaders.AUTHORIZATION, token)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(this.objectMapper.writeValueAsString(userDto))
                            .accept(MediaType.APPLICATION_JSON_UTF8)
            );

    // Then
    perform
            .andDo(print())
            .andExpect(status().isBadRequest());


    perform
            .andDo(
                    document("post-users-me")
            );

  }

}