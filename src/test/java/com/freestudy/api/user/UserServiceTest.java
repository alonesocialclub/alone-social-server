package com.freestudy.api.user;

import com.freestudy.api.interest.Interest;
import com.freestudy.api.interest.InterestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

  @Autowired
  UserService userService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  UserRepository userRepository;

  @Test
  public void createLocalAuthUserTest() {
    // Given
    User user = build();

    // When
    UserDetailsService userDetailsService = userService;
    UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

    // Then
    assertThat(userDetails.getPassword()).isEqualTo(user.getPassword());
    assertThat(userDetails.getUsername()).isEqualTo(user.getEmail());
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

  @Test
  public void saveTest__fix_email_only() {
    User user = build();
    var userName = user.getName();
    UserDto userDto = UserDto.builder().email("foo@test.com").build();

    userService.save(user, userDto);

    assertThat(user.getEmail()).isEqualTo(userDto.getEmail());
    assertThat(user.getName()).isEqualTo(userName);
  }

  @Test
  public void saveTest__fix_interests_only() {
    User user = build();
    var userName = user.getName();
    UserDto userDto = UserDto.builder().interests(Collections.singletonList(InterestDto.of("foo"))).build();

    userService.save(user, userDto);

    assertThat(
            user.getInterests().stream().map(Interest::getValue).collect(Collectors.toSet())
    ).isEqualTo(
            userDto.getInterests().stream().map(InterestDto::getValue).collect(Collectors.toSet())
    );
    assertThat(user.getName()).isEqualTo(userName);
  }

  private User build() {
    String name = "findByUsername";
    String email = "findByUsername@test.com";
    String password = "test";
    User user = User.builder()
            .name(name)
            .email(email)
            .password(passwordEncoder.encode(password))
            .build();

    return userRepository.save(user);
  }
}