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
                .andExpect(MockMvcResultMatchers.jsonPath("user.id").isNumber)
                .andExpect(MockMvcResultMatchers.jsonPath("authToken").isString)


        perform
                .andDo(
                        MockMvcRestDocumentation.document("anony-join")
                )
    }
}