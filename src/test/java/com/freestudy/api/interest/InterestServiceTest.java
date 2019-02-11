package com.freestudy.api.interest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class InterestServiceTest {

  @Autowired
  InterestService interestService;

  @Autowired
  InterestRepository interestRepository;

  @Test
  public void saveAllTest() throws Exception {

    // given
    HashSet<Interest> interests = new HashSet<>();
    interests.add(new Interest("과학2"));
    interests.add(new Interest("스타트업2"));
    interests.add(new Interest("통계1"));
    interestRepository.saveAll(interests);
    List<InterestDto> valuesToBeSaved = interests.stream().map(o -> InterestDto.of(o.getValue())).collect(Collectors.toList());
    valuesToBeSaved.add(InterestDto.of("사후세계"));

    // when
    HashSet<Interest> results = interestService.saveAll(valuesToBeSaved);

    // then
    assertThat(results).containsAll(interests);
    assertThat(results.size()).isEqualTo(valuesToBeSaved.size());
    assertThat(
            results.stream().map(Interest::getValue).collect(Collectors.toSet())
    ).isEqualTo(
            valuesToBeSaved.stream().map(InterestDto::getValue).collect(Collectors.toSet())
    );
  }

}