package social.alone.server.user;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.transaction.BeforeTransaction;
import social.alone.server.BaseIntegrateTest;
import social.alone.server.interest.InterestDto;

import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerIntegrationTest extends BaseIntegrateTest {

    @Test
    @WithUserDetails(value = USER_EMAIL, userDetailsServiceBeanName = "userService")
    public void getUsersMeTest() throws Exception {
        // When
        var perform = mockMvc
                .perform(
                        get("/api/users/me")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                );

        // Then
        perform
                .andDo(print())
                .andDo(
                        document("get-user-me")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("id").value(this.user.getId()));
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
        var token = buildAuthToken();

        List<InterestDto> interestDtoList = Arrays.asList(
                InterestDto.builder().value("서예").build(),
                InterestDto.builder().value("스타트업").build()
        );

        UserDto userDto = UserDto.builder()
                .name("foo")
                .email("putUserMeTest@email.com")
                .interests(interestDtoList)
                .build();
        System.out.println("======================");

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
                .andExpect(jsonPath("$.interests.length()").value(interestDtoList.size()));


        perform
                .andDo(
                        document(
                                "put-users-me"
                        )
                );
        mockMvc
                .perform(
                        get("/api/users/me")
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                ).andDo(print());

    }

    @Test
    public void putUsersMeTest__too_much_interests() throws Exception {
        // Given
        var token = buildAuthToken();
        List<InterestDto> interestDtoList = Arrays.asList(
                InterestDto.of("사후세계"),
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
        var token = buildAuthToken();
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

    }

    @Test
    public void getUsersId() throws Exception {
        // Given
        var user = createUser();

        // When
        var perform = mockMvc
                .perform(
                        get("/api/users/{id}", user.getId())
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                );

        // Then
        perform
                .andDo(print())
                .andDo(
                        document("get-user-id")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(user.getId()));
    }
}