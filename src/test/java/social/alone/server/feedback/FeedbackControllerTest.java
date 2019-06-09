package social.alone.server.feedback;


import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import social.alone.server.BaseIntegrateTest;
import social.alone.server.common.infrastructure.slack.SlackNotifier;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedRequestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FeedbackControllerTest extends BaseIntegrateTest {

    @Mock
    SlackNotifier slackNotifier;

    @Test
    @WithUserDetails(value = CREATED_USER_EMAIL, userDetailsServiceBeanName = "userService")
    public void createEventTest() throws Exception {
        
        // Given
        ResultActions perform = mockMvc
                .perform(
                        post("/api/feedbacks/")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content("{\"text\": \"바꿔야 할 것은 단 하나 전부입니다\"}")
                );

        // Then
        perform
                .andDo(print())
                .andExpect(status().isNoContent())
                .andDo(
                        document("post-feedbacks",
                                relaxedRequestFields(
                                        fieldWithPath("text").description("피드백 내용")
                                )
                        )
                );
    }
}