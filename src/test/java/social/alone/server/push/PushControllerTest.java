package social.alone.server.push;


import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import social.alone.server.BaseIntegrateTest;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class PushControllerTest extends BaseIntegrateTest {


    @Test
    @WithUserDetails(value = CREATED_USER_EMAIL, userDetailsServiceBeanName = "userService")
    public void pushtest() throws Exception {
        // Given & When
        ResultActions perform = mockMvc
                .perform(
                        post("/api/push/tokens")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content("{\"fcmToken\": \"foobar\"}")
                );

        // Then
        perform
                .andDo(print())
                .andDo(document("push-token-create"));
    }
}