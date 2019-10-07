package social.alone.server.event.controller

import org.junit.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import social.alone.server.BaseIntegrateTest

class EventUserControllerTest : BaseIntegrateTest() {

    @Test
    @Throws(Exception::class)
    fun eventJoin() {
        // Given
        val event = createEvent()
        // When
        val perform = this.mockMvc.perform(
                post("/api/events/{id}/users", event.id!!)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header(HttpHeaders.AUTHORIZATION, createUserAndBuildAuthToken())
        )

        // Then
        perform.andDo(print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.users.length()").value(1))

        perform.andDo(document("events-users-update"))
    }

    @Test
    @Throws(Exception::class)
    fun eventJoinCancel() {
        // Given
        val event = createEvent()
        val token = createUserAndBuildAuthToken()
        // When
        this.mockMvc.perform(
                post("/api/events/{id}/users", event.id!!)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header(HttpHeaders.AUTHORIZATION, token)
        )
        val perform = this.mockMvc.perform(
                delete("/api/events/{id}/users", event.id!!)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header(HttpHeaders.AUTHORIZATION, token)
        )

        // Then
        perform.andDo(print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.users.length()").value(0))

        perform.andDo(document("events-users-update-cancel"))
    }

}