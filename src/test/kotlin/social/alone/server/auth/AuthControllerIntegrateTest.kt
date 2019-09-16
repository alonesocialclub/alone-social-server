package social.alone.server.auth

import org.junit.Test
import org.springframework.hateoas.MediaTypes
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import social.alone.server.BaseIntegrateTest
import social.alone.server.auth.email.LoginRequestDto
import social.alone.server.auth.email.SignUpRequestDto

class AuthControllerIntegrateTest : BaseIntegrateTest() {


    @Test
    @Throws(Exception::class)
    fun signupTest() {
        // Given
        val data = SignUpRequestDto(
                "signupTest@test.com",
                "12341234",
                "jeff@amazon.com"
        )
        // When
        val perform = mockMvc.perform(
                post("/api/auth/signup/email")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(data))
        )

        // Then
        perform
                .andDo(print())
                .andExpect(status().isCreated)
                .andExpect(jsonPath("user.id").isNumber)
                .andExpect(jsonPath("token").isString)

        perform
                .andDo(
                        document(
                                "signup-email",
                                requestFields(
                                        fieldWithPath("email").description("로그인에 사용할 이메일"),
                                        fieldWithPath("password").description("비밀번호"),
                                        fieldWithPath("name").description("이름")
                                )

                        )
                )
    }

    @Test
    @Throws(Exception::class)
    fun loginTest() {

        // given
        val signUpRequestDto = SignUpRequestDto(
                "signupTest@test.com",
                "12341234",
                "jeff@amazon.com"
        )

        mockMvc.perform(
                post("/api/auth/signup/email")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(signUpRequestDto))
        ).andExpect(status().isCreated)

        // When

        val perform = mockMvc.perform(
                post("/api/auth/login/email")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(LoginRequestDto(signUpRequestDto.email, signUpRequestDto.password)))
        )

        // Then
        perform.andDo(print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("user.id").isNumber)
                .andExpect(jsonPath("token").isString)

        perform.andDo(
                document(
                        "login-email",
                        requestFields(
                                fieldWithPath("email").description("로그인에 사용할 이메일"),
                                fieldWithPath("password").description("비밀번호")
                        )

                )
        )
    }


    @Test
    @Throws(Exception::class)
    fun loginNotFound() {
        val loginRequestDto = LoginRequestDto("notfound@email.com", "123123")

        // When
        val perform = mockMvc.perform(
                post("/api/auth/login/email")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(loginRequestDto))
        )

        // Then
        perform.andDo(print())
                .andExpect(status().isNotFound)
    }

}