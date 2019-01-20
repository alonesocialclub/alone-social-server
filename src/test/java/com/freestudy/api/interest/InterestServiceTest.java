package com.freestudy.api.interest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class InterestServiceTest {

  @Autowired
  InterestService interestService;

  @Autowired
  InterestRepository interestRepository;

  @Test
  public void saveAllTest() throws Exception {

    // given
    Set<Interest> interests = new HashSet<>();
    interests.add(buildInterest("과학"));
    interests.add(buildInterest("스타트업"));
    interests.add(buildInterest("통계"));
    interestRepository.saveAll(interests);
    List<String> valuesToBeSaved = interests.stream().map(Interest::getValue).collect(Collectors.toList());
    valuesToBeSaved.add("사후세계");

    // when
    Set<Interest> results = interestService.saveAll(valuesToBeSaved);

    // then
    assertThat(results).containsAll(interests);
    assertThat(results.size()).isEqualTo(valuesToBeSaved.size());
    assertThat(results.stream().map(Interest::getValue).collect(Collectors.toList())).isEqualTo(valuesToBeSaved);
  }

  // TODO MAKE IT DRY, baseDAOTest
  private Interest buildInterest(String value) {
    return Interest
                    .builder()
                    .value(value)
                    .build()
    ;
  }

}