package com.freestudy.api.config;

import com.freestudy.api.BaseControllerTest;
import com.freestudy.api.DisplayName;
import com.freestudy.api.account.Account;
import com.freestudy.api.account.AccountRole;
import com.freestudy.api.account.AccountService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthServerConfigTest extends BaseControllerTest {

  @Autowired
  AccountService accountService;

  @Test
  @DisplayName("get auth token with password grant type")
  public void getAuthTokenTest() throws Exception {
    // Given
    String email = "jh2222@token-test.com";
    String password = "1234";
    Account account = Account.builder()
            .email(email)
            .password(password)
            .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
            .build();
    accountService.saveAccount(account);

    String clientId = "myApp";
    String clientSecret = "pass";

    // Then
    var perform = this.mockMvc.perform(post("/oauth/token")
            .with(httpBasic(clientId, clientSecret))
            .param("username", email)
            .param("password", password)
            .param("grant_type", "password"));

    // Then
    perform
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("access_token").exists());
  }
}