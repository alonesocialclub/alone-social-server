package social.alone.server.auth

import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import social.alone.server.user.domain.User
import social.alone.server.user.service.UserEnrollService
import social.alone.server.user.repository.UserRepository

import org.mockito.BDDMockito.given
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import social.alone.server.makeUser


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
@Ignore
class AuthControllerMockTest {

    @Autowired
    internal lateinit var mockMvc: MockMvc

    @InjectMocks
    internal lateinit var  authController: AuthController

    @Mock
    internal lateinit var userEnrollService: UserEnrollService

    @Mock
    internal lateinit var authTokenGenerator: AuthTokenGenerator

    @Mock
    internal lateinit var userRepository: UserRepository

    @Test
    @Throws(Exception::class)
    fun facebook() {
        val user = makeUser()
        val facebookAccessToken = "abcde"
        given(userEnrollService.byFacebook(facebookAccessToken)).willReturn(user)
        given(authTokenGenerator.byUser(user)).willReturn("fofofofofofofo")


        val perform = mockMvc.perform(
                post("/api/auth/login/facebook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"facebookAccessToken\": \"$facebookAccessToken\"}")
        )

        perform.andDo(print())
        perform
                .andDo(
                        document(
                                "login-facebook",
                                requestFields(
                                        fieldWithPath("facebookAccessToken").description("페이스북 엑세스 토큰")
                                )
                        )
                )
    }
}

