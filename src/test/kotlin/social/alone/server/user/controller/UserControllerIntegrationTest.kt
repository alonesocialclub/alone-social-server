package social.alone.server.user.controller

import org.junit.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.web.servlet.ResultActions
import social.alone.server.BaseIntegrateTest
import social.alone.server.interest.InterestDto
import social.alone.server.user.dto.UserDto

import java.util.ArrayList
import java.util.Arrays

import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


class UserControllerIntegrationTest : BaseIntegrateTest() {

    @Test
    @WithUserDetails(value = BaseIntegrateTest.CREATED_USER_EMAIL, userDetailsServiceBeanName = "customUserDetailService")
    @Throws(Exception::class)
    fun getUsersMeTest() {
        // When
        val perform = mockMvc
                .perform(
                        get("/api/users/me")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )

        // Then
        perform
                .andDo(print())
                .andDo(
                        document("get-user-me")
                )
                .andExpect(status().isOk)
                .andExpect(jsonPath("user.id").isNotEmpty)
                .andExpect(jsonPath("user.id").value(this.createdUser.id!!))
    }

    @Test
    @Throws(Exception::class)
    fun getUsersMeTest__without_token() {
        // Given & When
        val perform = mockMvc
                .perform(
                        get("/api/users/me")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )

        // Then
        perform.andExpect(status().isUnauthorized)
                .andDo(print())
    }

    @Test
    @Throws(Exception::class)
    fun putUsersMeTest() {
        // Given
        val token = createUserAndBuildAuthToken()
        val interestDtoList = Arrays.asList(
                InterestDto("서예"),
                InterestDto("스타트업")
        )

        val userDto = UserDto(
                "foo",
                "putUserMeTest@email.com",
                interestDtoList
        )

        // When
        val perform = mockMvc
                .perform(
                        put("/api/users/me")
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(this.objectMapper.writeValueAsString(userDto))
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )

        // Then
        perform
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("user.profile.name").isNotEmpty)
                .andExpect(jsonPath("user.email").doesNotExist())
                .andExpect(jsonPath("user.interests.length()").value(interestDtoList.size))


        perform
                .andDo(
                        document(
                                "put-users-me"
                        )
                )
        mockMvc
                .perform(
                        get("/api/users/me")
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                ).andDo(print())

    }

    @Test
    @Throws(Exception::class)
    fun putUsersMeTest__too_much_interests() {
        // Given
        val token = createUserAndBuildAuthToken()
        val interestDtoList = Arrays.asList(
                InterestDto("사후세계"),
                InterestDto("스타트업"),
                InterestDto("배구"),
                InterestDto("그림"),
                InterestDto("역사")
        )

        val userDto = UserDto(
                "foo",
                "putUserMeTest@email.com",
                interestDtoList
        )

        // When
        val perform = mockMvc
                .perform(
                        put("/api/users/me")
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(this.objectMapper.writeValueAsString(userDto))
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )

        // Then
        perform
                .andDo(print())
                .andExpect(status().isBadRequest)
    }

    @Test
    @Throws(Exception::class)
    fun putUsersMeTest__invalid_email_format() {
        // Given
        val token = createUserAndBuildAuthToken()
        val userDto = UserDto(
                "foo",
                "invalid_email_format",
                ArrayList()
        )
        // When
        val perform = mockMvc

                .perform(
                        put("/api/users/me")
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(this.objectMapper.writeValueAsString(userDto))
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )

        // Then
        perform
                .andDo(print())
                .andExpect(status().isBadRequest)

    }

    @Test
    @WithUserDetails(value = BaseIntegrateTest.CREATED_USER_EMAIL, userDetailsServiceBeanName = "customUserDetailService")
    @Throws(Exception::class)
    fun getUsersId() {
        // When
        val perform = mockMvc
                .perform(
                        get("/api/users/{id}", createdUser.id)
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )

        // Then
        perform
                .andDo(print())
                .andDo(
                        document("get-user-id")
                )
                .andExpect(status().isOk)
                .andExpect(jsonPath("user.id").value(createdUser.id!!))
    }
}