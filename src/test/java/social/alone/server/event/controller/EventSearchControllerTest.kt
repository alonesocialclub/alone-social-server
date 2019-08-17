package social.alone.server.event.controller

import org.junit.Ignore
import org.junit.Test
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import social.alone.server.BaseIntegrateTest
import social.alone.server.DisplayName
import social.alone.server.location.Location
import java.util.stream.IntStream

class EventSearchControllerTest : BaseIntegrateTest() {

    @Test
    @DisplayName("30개의 이벤트를 페이징 조회")
    @Throws(Exception::class)
    fun queryEvents__happy() {
        // Given
        IntStream.range(0, 10).forEach { this.createEvent() }

        // When
        val perform = this.mockMvc.perform(
                get("/api/events")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .param("page", "0")
                        .param("size", "2")
                        .param("sort", "name,desc")
                        .param("longitude", "37.477117")
                        .param("latitude", "126.961224")
        )

        // Then
        perform
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("content[0].id").exists())
                .andDo(
                        document("query-events",
                                requestParameters(
                                        parameterWithName("page").description("페이지"),
                                        parameterWithName("size").description("페이지의 크기"),
                                        parameterWithName("sort").description("<:field>,<:sort> 형태. 값을 URL encoding 해야한다. 예시 참고"),
                                        parameterWithName("longitude").description("경도"),
                                        parameterWithName("latitude").description("위도")
                                )
                        ))
    }

    @Test
    @DisplayName("내가 만든 이벤트")
    @Throws(Exception::class)
    fun queryEvents__type_owner() {
        // Given
        IntStream.range(0, 10).forEach { this.createEvent() }

        // When
        val perform = this.mockMvc.perform(
                get("/api/events")
                        .param("type", "OWNER")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        )

        // Then
        perform
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("content.length()").value(10))
    }

    @Test
    @DisplayName("좌표 기반 쿼리, 가까운 순으로 나와야 함")
    @Ignore
    @Throws(Exception::class)
    fun queryEvents__location() {
        // Given
        val eventFar = this.createEvent(Location(
                "역삼",
                "할리스",
                127.0318613,
                37.4991894,
                "http://place.map.daum.net/27290899"
        ))
        val eventNear = this.createEvent(Location(
                "낙성대",
                "가빈 커피로스터즈",
                126.9630652,
                37.4765389,
                "http://place.map.daum.net/27290899"
        ))
        val eventFarFar = this.createEvent(Location(
                "강릉 카페",
                "바다가 보인다",
                128.8218548,
                37.8228477,
                "http://place.map.daum.net/27290899"
        ))


        // When
        val perform = this.mockMvc.perform(
                get("/api/events")
                        .param("longitude", eventNear.location.longitude!!.toString())
                        .param("latitude", eventNear.location.latitude!!.toString())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        )

        // Then
        perform
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("content.length()").value(3))
                .andExpect(jsonPath("content[0].id").value(eventNear.id!!))
                .andExpect(jsonPath("content[1].id").value(eventFar.id!!))
                .andExpect(jsonPath("content[2].id").value(eventFarFar.id!!))
    }

    @Test
    @DisplayName("기존 이벤트 하나 조회")
    @Throws(Exception::class)
    fun getEvent() {
        // Given
        val event = createEvent()

        // When
        val perform = this.mockMvc.perform(get("/api/events/{id}", event.id))

        // Then
        perform.andExpect(status().isOk)
        perform.andExpect(jsonPath("id").exists())
        perform.andExpect(jsonPath("name").exists())
        perform.andExpect(jsonPath("description").exists())
        perform.andDo(
                document("get-event",
                        pathParameters(
                                parameterWithName("id").description("event id")

                        )
                ))
    }


    @Test
    @DisplayName("기존 이벤트 하나 조회, 이벤트가 없을 때")
    @Throws(Exception::class)
    fun getEvent__not_found() {
        // When
        val perform = this.mockMvc.perform(get("/api/events/{id}", 0))

        // Then
        perform.andExpect(status().isNotFound)
    }
}