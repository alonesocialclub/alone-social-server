package social.alone.server.oauth

import org.junit.Test
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.requestParameters
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import social.alone.server.BaseIntegrateTest

class OauthIntegrateTest : BaseIntegrateTest() {

    @Test
    @Throws(Exception::class)
    fun testOauth2AuthorizeFacebook() {

        val perform = mockMvc.perform(
                get("/oauth2/authorize/facebook")
                        .param("redirect_uri", "https://myweb.com")
        )

        perform.andDo(print())
                .andExpect(status().is3xxRedirection)
                .andExpect(redirectedUrlPattern("https://www.facebook.com/**"))

        perform.andDo(
                document(
                        "oauth2-facebook",
                        requestParameters(
                                parameterWithName("redirect_uri").description("인증 후 리다이렉트 될 url이며 화이트리스트 기반으로 관리된다.")
                        )
                )
        )
    }
}
