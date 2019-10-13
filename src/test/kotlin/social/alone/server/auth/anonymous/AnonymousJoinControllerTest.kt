package social.alone.server.auth.anonymous

import org.junit.Test
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import social.alone.server.BaseIntegrateTest

class AnonymousJoinControllerTest : BaseIntegrateTest() {

    @Test
    @Throws(Exception::class)
    fun join() {
        // Given
        val data = JoinByProfileRequest(ProfileRequest("fred"))
        // When
        val perform = mockMvc.perform(
                MockMvcRequestBuilders.post("/anonymous/join")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(data))
        )

        // Then
        perform
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("user.id").isString)
                .andExpect(MockMvcResultMatchers.jsonPath("authToken").isString)


        perform
                .andDo(
                        MockMvcRestDocumentation.document("anonymous-join")
                )
    }

    @Test
    @Throws(Exception::class)
    fun join__empty_name() {
        // Given
        val data = JoinByProfileRequest(ProfileRequest(""))
        // When
        val perform = mockMvc.perform(
                MockMvcRequestBuilders.post("/anonymous/join")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(data))
        )

        // Then
        perform
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andExpect(MockMvcResultMatchers.jsonPath("errors.[0].field").value("profile.name"))
                .andExpect(MockMvcResultMatchers.jsonPath("errors.[0].code").value("NotEmpty"))
                .andExpect(MockMvcResultMatchers.jsonPath("errors.[0].defaultMessage").isString)
    }

    @Test
    @Throws(Exception::class)
    fun join__too_long_name() {
        // Given
        val data = JoinByProfileRequest(ProfileRequest("aasldkjasdlkajsdlaksjdlakdjalskdjalsdkjalskdjaslk"))
        // When
        val perform = mockMvc.perform(
                MockMvcRequestBuilders.post("/anonymous/join")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(data))
        )

        // Then
        perform
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andExpect(MockMvcResultMatchers.jsonPath("errors.[0].field").value("profile.name"))
                .andExpect(MockMvcResultMatchers.jsonPath("errors.[0].code").value("Size"))
                .andExpect(MockMvcResultMatchers.jsonPath("errors.[0].defaultMessage").isString)
    }
}