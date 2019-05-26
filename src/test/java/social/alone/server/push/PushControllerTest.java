package social.alone.server.push;


import org.junit.Test;
import org.springframework.http.MediaType;
import social.alone.server.BaseIntegrateTest;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class PushControllerTest  extends BaseIntegrateTest {


    @Test
    public void pushtest() throws Exception {
        // Given & When
        var perform = mockMvc
                .perform(
                        post("/api/push/tokens")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\"fcmToken\": \"f6USRsHqSow:APA91bGvQw1iFjyIwpW2T-ezbRJ-v6XH7KrXlTpp0rIdnyPmBUxa4RMz0JWHjIomISGDKMNRt31dAt952ag3cCglMbWjRb656cfQUJ8NcwEwCLULPY4ofJRFVlj0RBOHWir4taW_77fX\"}")
                );

        // Then
        perform.andDo(print());
    }
}