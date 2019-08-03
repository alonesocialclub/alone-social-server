package social.alone.server.push


import org.junit.Test
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import social.alone.server.BaseIntegrateTest

class PushControllerTest : BaseIntegrateTest() {

    @Test
    @WithUserDetails(value = CREATED_USER_EMAIL, userDetailsServiceBeanName = "customUserDetailService")
    @Throws(Exception::class)
    fun pushtest() {
        // Given & When
        val perform = mockMvc
                .perform(
                        post("/api/push/tokens")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content("{\"fcmToken\": \"foobar\"}")
                )

        // Then
        perform
                .andDo(print())
                .andDo(document("push-token-create"))
    }
}