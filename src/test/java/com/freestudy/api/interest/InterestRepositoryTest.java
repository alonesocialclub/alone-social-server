package com.freestudy.api.interest;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class InterestRepositoryTest {

  @Autowired
  InterestRepository interestRepository;

  @Test
  public void saveTest() throws Exception {

    // given
    Interest interest = Interest
            .builder()
            .value("스타트업")
            .build();

    // when
    interestRepository.save(interest);

    // then
    assertThat(interest).isNotNull();
    assertThat(interest.getUsers()).isEmpty();
    assertThat(interest.getUsers()).isEqualTo(Set.of());
  }

}