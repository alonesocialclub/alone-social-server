package social.alone.server.feedback

import org.junit.Test
import org.mockito.Mock
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.relaxedRequestFields
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import social.alone.server.BaseIntegrateTest
import social.alone.server.slack.SlackNotifier

class FeedbackControllerTest : BaseIntegrateTest() {

    @Mock
    lateinit var slackNotifier: SlackNotifier

    @Test
    @WithUserDetails(value = BaseIntegrateTest.CREATED_USER_EMAIL, userDetailsServiceBeanName = "customUserDetailService")
    @Throws(Exception::class)
    fun createEventTest() {

        // Given
        val perform = mockMvc
                .perform(
                        post("/api/feedbacks/")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content("{\"text\": \"바꿔야 할 것은 단 하나 전부입니다\"}")
                )

        // Then
        perform
                .andDo(print())
                .andExpect(status().isNoContent)
                .andDo(
                        document("post-feedbacks",
                                relaxedRequestFields(
                                        fieldWithPath("text").description("피드백 내용")
                                )
                        )
                )
    }
}