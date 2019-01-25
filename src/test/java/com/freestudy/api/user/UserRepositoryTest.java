package com.freestudy.api.user;

import com.freestudy.api.interest.Interest;
import com.freestudy.api.interest.InterestRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @Autowired
  InterestRepository interestRepository;

  @Test
  public void userSaveTest() {
    // given
    User user = User.builder()
            .email("userSaveTest@test.com")
            .name("foo")
            .build();

    // when
    User result = userRepository.save(user);

    // then
    assertThat(user.getId()).isNotNull();
    assertThat(result).isEqualTo(user);
  }

  @Test
  public void userSaveWithInterestsTest() {
    // given
    Interest interest = Interest.builder().value("develop").build();
    User user = User.builder()
            .email("userSaveTest@test.com")
            .name("foo")
            .build();

    // when
    user.getInterests().add(interest);
    interest.getUsers().add(user);
    userRepository.save(user);

    // then
    assertThat(user.getInterests()).contains(interest);
    assertThat(interest.getUsers()).contains(user);
    // cascade persistent
    assertThat(interest.getId()).isNotNull();
  }
}