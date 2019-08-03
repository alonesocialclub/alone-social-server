package social.alone.server.user;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import social.alone.server.BaseIntegrateTest;
import social.alone.server.interest.InterestDto;
import social.alone.server.user.dto.UserDto;

import java.util.ArrayList;
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
    @WithUserDetails(value = CREATED_USER_EMAIL, userDetailsServiceBeanName = "customUserDetailService")
    public void getUsersMeTest() throws Exception {
        // When
        ResultActions perform = mockMvc
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
                .andExpect(jsonPath("user.id").isNotEmpty())
                .andExpect(jsonPath("user.id").value(this.createdUser.getId()));
    }

    @Test
    public void getUsersMeTest__without_token() throws Exception {
        // Given & When
        ResultActions perform = mockMvc
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
        String token = createUserAndBuildAuthToken();
        List<InterestDto> interestDtoList = Arrays.asList(
                new InterestDto("서예"),
                new InterestDto("스타트업")
        );

        UserDto userDto = new UserDto(
                "foo",
                "putUserMeTest@email.com",
                interestDtoList
        );

        // When
        ResultActions perform = mockMvc
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
                .andExpect(jsonPath("user.name").isNotEmpty())
                .andExpect(jsonPath("user.email").doesNotExist())
                .andExpect(jsonPath("user.interests.length()").value(interestDtoList.size()));


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
        String token = createUserAndBuildAuthToken();
        List<InterestDto> interestDtoList = Arrays.asList(
                new InterestDto("사후세계"),
                new InterestDto("스타트업"),
                new InterestDto("배구"),
                new InterestDto("그림"),
                new InterestDto("역사")
        );

        UserDto userDto = new UserDto(
                "foo",
                "putUserMeTest@email.com",
                interestDtoList
        );

        // When
        ResultActions perform = mockMvc
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
        String token = createUserAndBuildAuthToken();
        UserDto userDto = new UserDto(
                "foo",
                "invalid_email_format",
                new ArrayList<>()
        );
        // When
        ResultActions perform = mockMvc

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
    @WithUserDetails(value = CREATED_USER_EMAIL, userDetailsServiceBeanName = "customUserDetailService")
    public void getUsersId() throws Exception {
        // When
        ResultActions perform = mockMvc
                .perform(
                        get("/api/users/{id}", createdUser.getId())
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
                .andExpect(jsonPath("user.id").value(createdUser.getId()));
    }
}