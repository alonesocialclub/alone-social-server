package social.alone.server.auth;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import social.alone.server.user.User;
import social.alone.server.user.UserEnrollService;
import social.alone.server.user.UserRepository;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Ignore
public class AuthControllerMockTest {

    @Autowired
    MockMvc mockMvc;

    @InjectMocks
    AuthController authController;

    @Mock
    UserEnrollService userEnrollService;

    @Mock
    AuthTokenGenerator authTokenGenerator;

    @Mock
    UserRepository userRepository;

    @Test
    public void facebook() throws Exception {
        User user = new User("facebook@email.com", "1234", "local");
        String facebookAccessToken = "abcde";
        given(userEnrollService.byFacebook(facebookAccessToken)).willReturn(user);
        given(authTokenGenerator.byUser(user)).willReturn("fofofofofofofo");


        ResultActions perform = mockMvc.perform(
                post("/api/auth/login/facebook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"facebookAccessToken\": \""+ facebookAccessToken + "\"}")
        );

        perform.andDo(print());
        perform
                .andDo(
                        document(
                                "login-facebook",
                                requestFields(
                                        fieldWithPath("facebookAccessToken").description("페이스북 엑세스 토큰")
                                )
                        )
                );
    }
}

