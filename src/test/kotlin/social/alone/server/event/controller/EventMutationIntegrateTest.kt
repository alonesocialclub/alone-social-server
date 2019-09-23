package social.alone.server.event.controller

import org.junit.Test
import org.springframework.hateoas.MediaTypes
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import social.alone.server.BaseIntegrateTest
import social.alone.server.DisplayName
import social.alone.server.event.dto.EventDto
import social.alone.server.location.LocationDto
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashSet

class EventMutationIntegrateTest : BaseIntegrateTest() {

    @Test
    @WithUserDetails(value = BaseIntegrateTest.CREATED_USER_EMAIL, userDetailsServiceBeanName = "customUserDetailService")
    @Throws(Exception::class)
    fun createEventTest__happy() {
        // Given
        val eventType1 = createEventType("밥 같이 먹어요")
        val eventType2 = createEventType("조금 떠들어요")
        val eventTypes = HashSet(Arrays.asList(eventType1.toDto(), eventType2.toDto()))
        val event = EventDto(
                "오전 10시부터 오후 3시까지 각자 모여서 코딩합니다.",
                LocationDto(
                        "서울 서초구 강남대로61길 3",
                        "스타벅스",
                        127.026503385182,
                        37.4991561765984,
                        "http://place.map.daum.net/27290899"),
                LocalDateTime.of(2018, 11, 11, 12, 0),
                LocalDateTime.of(2018, 11, 11, 14, 0),
                eventTypes
        )

        // When
        val perform = mockMvc
                .perform(
                        post("/api/events/")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(objectMapper.writeValueAsString(event))
                )

        // Then
        perform
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("id").isNumber)
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("location.imageUrl").isNotEmpty)
                .andDo(document("post-events"))
    }

    @Test
    @WithUserDetails(value = BaseIntegrateTest.CREATED_USER_EMAIL, userDetailsServiceBeanName = "customUserDetailService")
    @Throws(Exception::class)
    fun createEventDuplicated__Test() {
        // Given
        val payload = "{\"name\":\"투썸플레이스 포스코사거리점\",\"description\":\"투썸플레이스 포스코사거리점\",\"location\":{\"name\":\"투썸플레이스 포스코사거리점\",\"address\":\"서울 강남구 테헤란로 508\",\"placeUrl\":\"http://place.map.daum.net/26452947\",\"latitude\":37.50670826384592,\"longitude\":127.0580393520872,\"imageUrl\":null},\"startedAt\":\"2019-04-28T14:09\",\"endedAt\":\"2019-04-28T17:09\",\"limitOfEnrollment\":5,\"eventTypes\":[]}"

        // When
        mockMvc
                .perform(
                        post("/api/events/")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(payload)
                )
                .andDo(print())

        // When
        val perform = mockMvc
                .perform(
                        post("/api/events/")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(payload)
                )

        // Then
        perform
                .andDo(print())
                .andExpect(status().isOk)
    }


    @Test
    @DisplayName("이벤트 시작일은 종료일보다 이전이여야 한다.")
    @WithUserDetails(value = BaseIntegrateTest.CREATED_USER_EMAIL, userDetailsServiceBeanName = "customUserDetailService")
    @Throws(Exception::class)
    fun createEventTest_invalid_input() {
        // Given
        val eventType1 = createEventType("밥 같이 먹어요")
        val eventType2 = createEventType("조금 떠들어요")
        val eventTypes = HashSet(Arrays.asList(eventType1.toDto(), eventType2.toDto()))
        val eventDto = EventDto(
                "낙성대 주말 코딩",
                LocationDto(
                        "서울 서초구 강남대로61길 3",
                        "스타벅스",
                        127.026503385182,
                        37.4991561765984,
                        "http://place.map.daum.net/27290899"),
                LocalDateTime.of(2018, 11, 11, 14, 0),
                LocalDateTime.of(2018, 11, 11, 12, 0),
                eventTypes
        )

        // When
        val perform = mockMvc
                .perform(
                        post("/api/events")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaTypes.HAL_JSON)
                                .content(objectMapper.writeValueAsString(eventDto))
                )

        // Then
        perform
                .andDo(print())
                .andExpect(status().isBadRequest)
    }

    @Test
    @DisplayName("이벤트 수정")
    @WithUserDetails(value = BaseIntegrateTest.CREATED_USER_EMAIL, userDetailsServiceBeanName = "customUserDetailService")
    @Throws(Exception::class)
    fun updateEvent__happy() {
        // Given
        val event = createEvent(this.createdUser)
        val description = "updated event"
        val eventDto = EventDto(
                description,
                LocationDto(
                        "서울 서초구 강남대로61길 3",
                        "스타벅스",
                        127.026503385182,
                        37.4991561765984,
                        "http://place.map.daum.net/27290899"),
                event.startedAt,
                event.endedAt
        )


        // When
        val perform = this.mockMvc.perform(
                put("/api/events/{id}", event.id)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(this.objectMapper.writeValueAsString(eventDto))
        )

        // Then
        perform.andDo(print())
        perform.andDo(document("events-update"))
        perform.andExpect(status().isOk)
        perform.andExpect(jsonPath("description").value(description))
    }

    @Test
    @DisplayName("이벤트 수정, 없는 이벤트에 대해서")
    @WithUserDetails(value = BaseIntegrateTest.CREATED_USER_EMAIL, userDetailsServiceBeanName = "customUserDetailService")
    @Throws(Exception::class)
    fun updateEvent__not_found() {
        // Given
        val eventType1 = createEventType("밥 같이 먹어요")
        val eventType2 = createEventType("조금 떠들어요")
        val eventTypes = HashSet(Arrays.asList(eventType1.toDto(), eventType2.toDto()))
        val eventDto = EventDto(
                "낙성대 주말 코딩",
                LocationDto(
                        "서울 서초구 강남대로61길 3",
                        "스타벅스",
                        127.026503385182,
                        37.4991561765984,
                        "http://place.map.daum.net/27290899"),
                LocalDateTime.of(2018, 11, 11, 12, 0),
                LocalDateTime.of(2018, 11, 11, 14, 0),
                eventTypes
        )
        val eventIdNotExists = -1

        // When
        val perform = this.mockMvc.perform(
                put("/api/events/{id}", eventIdNotExists)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(eventDto))
        )

        perform.andDo(print())
        perform.andExpect(status().isNotFound)
    }

    @Test
    @DisplayName("이벤트 수정, 시작시간을 종료시간 이후의 값을 넣는 경우")
    @WithUserDetails(value = BaseIntegrateTest.CREATED_USER_EMAIL, userDetailsServiceBeanName = "customUserDetailService")
    @Throws(Exception::class)
    fun updateEvent__invalid_startedAt_endedAt() {
        // Given
        val event = createEvent()
        val eventType1 = createEventType("밥 같이 먹어요")
        val eventType2 = createEventType("조금 떠들어요")
        val eventTypes = HashSet(Arrays.asList(eventType1.toDto(), eventType2.toDto()))
        val eventDto = EventDto(
                "오전 10시부터 오후 3시까지 각자 모여서 코딩합니다.",
                LocationDto(
                        "서울 서초구 강남대로61길 3",
                        "스타벅스",
                        127.026503385182,
                        37.4991561765984,
                        "http://place.map.daum.net/27290899"),
                LocalDateTime.of(2018, 11, 11, 18, 0),
                LocalDateTime.of(2018, 11, 11, 14, 0),
                eventTypes
        )

        // When
        val perform = this.mockMvc.perform(
                put("/api/events/{id}", event.id)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(this.objectMapper.writeValueAsString(eventDto))
        )

        // Then
        perform.andDo(print())
        perform.andExpect(status().isBadRequest)
    }

    @Test
    @DisplayName("이벤트 삭제")
    @WithUserDetails(value = BaseIntegrateTest.CREATED_USER_EMAIL, userDetailsServiceBeanName = "customUserDetailService")
    @Throws(Exception::class)
    fun deleteEvent() {
        // Given
        val event = createEvent(this.createdUser)

        // When
        val perform = this.mockMvc.perform(
                delete("/api/events/{id}", event.id!!)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )

        // Then
        perform.andDo(print())
        perform.andExpect(status().isNoContent)
    }

    @Test
    @DisplayName("이벤트 삭제, 없을 때")
    @WithUserDetails(value = BaseIntegrateTest.CREATED_USER_EMAIL, userDetailsServiceBeanName = "customUserDetailService")
    @Throws(Exception::class)
    fun deleteEvent__not_found() {
        // When
        val perform = this.mockMvc.perform(
                delete("/api/events/{id}", 0)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )

        // Then
        perform.andDo(print())
        perform.andExpect(status().isNotFound)
    }

    @Test
    @DisplayName("이벤트에 장소가 중복으로 insert 되지 않아야 한다")
    @WithUserDetails(value = BaseIntegrateTest.CREATED_USER_EMAIL, userDetailsServiceBeanName = "customUserDetailService")
    @Throws(Exception::class)
    fun eventLocationDuplicated() {
        // Given
        val eventType1 = createEventType("밥 같이 먹어요")
        val eventType2 = createEventType("조금 떠들어요")
        val eventTypes = HashSet(Arrays.asList(eventType1.toDto(), eventType2.toDto()))
        val eventDto1 = EventDto(
                "낙성대 주말 코딩",
                LocationDto(
                        "서울 서초구 강남대로61길 3",
                        "스타벅스",
                        127.026503385182,
                        37.4991561765984,
                        "http://place.map.daum.net/27290899"),
                LocalDateTime.of(2018, 11, 11, 12, 0),
                LocalDateTime.of(2018, 11, 11, 14, 0),
                eventTypes
        )

        val eventDto2 = EventDto(
                "오전 10시부터 오후 3시까지 각자 모여서 코딩합니다.",
                LocationDto(
                        "서울 서초구 강남대로61길 3",
                        "스타벅스",
                        127.026503385182,
                        37.4991561765984,
                        "http://place.map.daum.net/27290899"),
                LocalDateTime.of(2018, 11, 11, 12, 0),
                LocalDateTime.of(2018, 11, 11, 14, 0),
                eventTypes
        )

        // When
        mockMvc
                .perform(
                        post("/api/events/")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(objectMapper.writeValueAsString(eventDto1))
                )

        // When
        mockMvc
                .perform(
                        post("/api/events/")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(objectMapper.writeValueAsString(eventDto2))
                )

        val perform = this.mockMvc.perform(
                get("/api/events")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        )

        // Then
        perform.andDo(print())
                .andExpect(status().isOk)
    }

}
