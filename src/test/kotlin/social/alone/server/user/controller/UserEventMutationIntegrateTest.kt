package social.alone.server.user.controller


import org.junit.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions
import social.alone.server.BaseIntegrateTest
import social.alone.server.event.domain.Event

import java.util.Arrays

import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.requestParameters
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


class UserEventMutationIntegrateTest : BaseIntegrateTest() {

    @Test
    @Throws(Exception::class)
    fun queryEventsTest() {
        val eventList = Arrays.asList(this.createEvent(), this.createEvent())

        val perform = this.mockMvc.perform(
                get("/api/users/{id}/events", eventList[0].owner.id)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .param("type", "OWNER")
                        .param("page", "0")
                        .param("size", "2")
                        .param("sort", "name,desc")
        )

        perform.andDo(print())
        perform.andExpect(status().isOk)
        perform.andDo(
                document("get-events-by-user",
                        requestParameters(
                                parameterWithName("page").description("페이지"),
                                parameterWithName("size").description("페이지의 크기"),
                                parameterWithName("sort").description("<:field>,<:sort> 형태. 값을 URL encoding 해야한다. 예시 참고"),
                                parameterWithName("type").description("ALL,OWNER,JOINER")
                        )
                )
        )
    }


}