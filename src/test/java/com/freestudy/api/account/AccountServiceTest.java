package com.freestudy.api.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {

  @Autowired
  private AccountService accountService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  public void findByUsername() {
    // Given
    String username = "test@test.com";
    String password = "test";
    Account account = Account.builder()
            .email(username)
            .password(password)
            .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
            .build();
    accountService.saveAccount(account);

    // When
    UserDetailsService userDetailsService = accountService;
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    // Then
    assertThat(this.passwordEncoder.matches(password, userDetails.getPassword())).isTrue();
  }

  @Test
  public void findByUsername_Fail() {
    // Given
    String notFoundUserName = "fooo@testest.com";

    // When
    try {
      UserDetailsService userDetailsService = accountService;
      userDetailsService.loadUserByUsername(notFoundUserName); // username
      fail("supposed to be failed");
    } catch (UsernameNotFoundException e) {
      // Then
      assertThat(e.getMessage()).contains(notFoundUserName);
    }
  }

}