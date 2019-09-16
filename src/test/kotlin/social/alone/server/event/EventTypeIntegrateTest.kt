package social.alone.server.event

import org.junit.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions
import social.alone.server.BaseIntegrateTest
import social.alone.server.DisplayName

import java.util.stream.IntStream

import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class EventTypeIntegrateTest : BaseIntegrateTest() {

    @Test
    @DisplayName("이벤트 타입을 조회")
    @Throws(Exception::class)
    fun queryEvents__happy() {
        // Given
        IntStream.range(0, 10).forEach { i -> this.createEventType("event category$i") }

        // When
        val perform = this.mockMvc.perform(
                get("/api/event-types")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        )

        // Then
        perform
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("content[0].id").exists())
                .andExpect(jsonPath("content[0].value").exists())
                .andDo(
                        document("query-event-types"))
    }

}