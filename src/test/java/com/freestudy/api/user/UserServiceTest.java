package com.freestudy.api.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

  @Autowired
  UserService userService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Test
  public void createLocalAuthUserTest() {
    // Given
    String name = "findByUsername";
    String email = "findByUsername@test.com";
    String password = "test";
    userService.createLocalAuthUser(
            name, email, password
    );

    // When
    UserDetailsService userDetailsService = userService;
    UserDetails userDetails = userDetailsService.loadUserByUsername(email);

    // Then
    assertThat(this.passwordEncoder.matches(password, userDetails.getPassword())).isTrue();
    assertThat(userDetails.getUsername()).isEqualTo(email);
  }

  @Test
  public void findByUsernameTest_not_found() {
    // Given
    String notFoundUserName = "failfailfailnotexists@fail.com";

    // When
    try {
      UserDetailsService userDetailsService = userService;
      userDetailsService.loadUserByUsername(notFoundUserName); // username
      fail("supposed to be failed");
    } catch (UsernameNotFoundException e) {
      // Then
      assertThat(e.getMessage()).contains(notFoundUserName);
    }
  }
}