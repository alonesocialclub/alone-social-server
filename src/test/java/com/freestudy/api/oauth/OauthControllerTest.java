package com.freestudy.api.oauth;

import com.freestudy.api.BaseControllerTest;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OauthControllerTest extends BaseControllerTest {

  @Test
  public void testOauth2AuthorizeFacebook() throws Exception {

    var result = mockMvc.perform(get("/oauth2/authorize/facebook"));

    result.andDo(print());
    result.andExpect(status().is3xxRedirection());
    result.andExpect(redirectedUrlPattern("https://www.facebook.com/**"));
  }
}
