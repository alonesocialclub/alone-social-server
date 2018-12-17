package com.freestudy.api.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {

  @Autowired
  private AccountService accountService;

  @Autowired
  private AccountRepository accountRepository;

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
    this.accountRepository.save(account);

    // When
    UserDetailsService userDetailsService = accountService;
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    // Then
    assertThat(userDetails.getPassword()).isEqualTo(password);
  }

}