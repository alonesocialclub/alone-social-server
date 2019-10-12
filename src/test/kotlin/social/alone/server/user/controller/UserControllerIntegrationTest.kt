package social.alone.server.user.controller

import org.junit.Test
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import social.alone.server.BaseIntegrateTest


class UserControllerIntegrationTest : BaseIntegrateTest() {

    @Test
    @WithUserDetails(value = BaseIntegrateTest.CREATED_USER_EMAIL, userDetailsServiceBeanName = "customUserDetailService")
    @Throws(Exception::class)
    fun getUsersMeTest() {
        // When
        val perform = mockMvc
                .perform(
                        get("/me")
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
                .andExpect(jsonPath("id").isNotEmpty)
                .andExpect(jsonPath("id").value(this.createdUser.id!!))
    }

    @Test
    @Throws(Exception::class)
    fun getUsersMeTest__without_token() {
        // Given & When
        val perform = mockMvc
                .perform(
                        get("/me")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )

        // Then
        perform.andExpect(status().isUnauthorized)
                .andDo(print())
    }

    @Test
    @WithUserDetails(value = BaseIntegrateTest.CREATED_USER_EMAIL, userDetailsServiceBeanName = "customUserDetailService")
    @Throws(Exception::class)
    fun getUsersId() {
        // When
        val perform = mockMvc
                .perform(
                        get("/users/{id}", createdUser.id)
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
                .andExpect(jsonPath("id").value(createdUser.id!!))
    }
}