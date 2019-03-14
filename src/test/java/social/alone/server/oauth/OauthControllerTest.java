package social.alone.server.oauth;

import social.alone.server.BaseControllerTest;
import org.junit.Test;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OauthControllerTest extends BaseControllerTest {

  @Test
  public void testOauth2AuthorizeFacebook() throws Exception {

    var perform = mockMvc.perform(
            get("/oauth2/authorize/facebook")
                    .param("redirect_uri", "https://myweb.com")
    );

    perform.andDo(print())
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrlPattern("https://www.facebook.com/**"));

    perform.andDo(
            document(
                    "oauth2-facebook",
                    requestParameters(
                            parameterWithName("redirect_uri").description("인증 후 리다이렉트 될 url이며 화이트리스트 기반으로 관리된다.")
                    )
            )
    );
  }
}
