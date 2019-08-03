//package social.alone.server.auth;
//
//import org.junit.Ignore;
//import org.junit.Test;
//import org.springframework.hateoas.MediaTypes;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.ResultActions;
//import social.alone.server.BaseIntegrateTest;
//import social.alone.server.auth.email.LoginRequestDto;
//import social.alone.server.auth.email.SignUpRequestDto;
//
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
//import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class AuthControllerIntegrateTest extends BaseIntegrateTest {
//
//
//    @Test
//    public void signupTest() throws Exception {
//        // Given
//        SignUpRequestDto data = SignUpRequestDto.builder()
//                .email("signupTest@test.com")
//                .password("123456785678")
//                .name("Jeff")
//                .build();
//
//        // When
//        ResultActions perform = mockMvc.perform(
//                post("/api/auth/signup/email")
//                        .contentType(MediaType.APPLICATION_JSON_UTF8)
//                        .accept(MediaTypes.HAL_JSON)
//                        .content(objectMapper.writeValueAsString(data))
//        );
//
//        // Then
//        perform
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("id").isNumber())
//                .andExpect(jsonPath("token").isString());
//
//        perform
//                .andDo(
//                        document(
//                                "signup-email",
//                                requestFields(
//                                        fieldWithPath("email").description("로그인에 사용할 이메일"),
//                                        fieldWithPath("password").description("비밀번호"),
//                                        fieldWithPath("name").description("이름")
//                                )
//
//                        )
//                );
//    }
//
//    @Test
//    public void loginTest() throws Exception {
//
//        // given
//        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
//                .email("loginTest@test.com")
//                .password("12345678")
//                .name("Jeff")
//                .build();
//
//        mockMvc.perform(
//                post("/api/auth/signup/email")
//                        .contentType(MediaType.APPLICATION_JSON_UTF8)
//                        .content(objectMapper.writeValueAsString(signUpRequestDto))
//        ).andExpect(status().isCreated());
//
//        // When
//        LoginRequestDto loginRequestDto = modelMapper.map(signUpRequestDto, LoginRequestDto.class);
//        ResultActions perform = mockMvc.perform(
//                post("/api/auth/login/email")
//                        .contentType(MediaType.APPLICATION_JSON_UTF8)
//                        .content(objectMapper.writeValueAsString(loginRequestDto))
//        );
//
//        // Then
//        perform.andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("id").isNumber())
//                .andExpect(jsonPath("token").isString());
//
//        perform.andDo(
//                document(
//                        "login-email",
//                        requestFields(
//                                fieldWithPath("email").description("로그인에 사용할 이메일"),
//                                fieldWithPath("password").description("비밀번호")
//                        )
//
//                )
//        );
//    }
//
//    @Test
//    public void loginInvalidDto() throws Exception {
//        LoginRequestDto loginRequestDto = new LoginRequestDto("notemail", "");
//
//        // When
//        ResultActions perform = mockMvc.perform(
//                post("/api/auth/login/email")
//                        .contentType(MediaType.APPLICATION_JSON_UTF8)
//                        .content(objectMapper.writeValueAsString(loginRequestDto))
//        );
//
//        // Then
//        perform.andDo(print())
//                .andExpect(status().isBadRequest());
//    }
//
//
//    @Test
//    public void loginNotFound() throws Exception {
//        LoginRequestDto loginRequestDto = new LoginRequestDto("notfound@email.com", "123123");
//
//        // When
//        ResultActions perform = mockMvc.perform(
//                post("/api/auth/login/email")
//                        .contentType(MediaType.APPLICATION_JSON_UTF8)
//                        .content(objectMapper.writeValueAsString(loginRequestDto))
//        );
//
//        // Then
//        perform.andDo(print())
//                .andExpect(status().isNotFound());
//    }
//
//}