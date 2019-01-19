package com.freestudy.api.interest;


import com.freestudy.api.BaseDaoTest;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class InterestRepositoryTest extends BaseDaoTest {


  @Test
  public void saveTest() throws Exception {

    // given
    Interest interest = Interest.builder().value("스타트업").build();
    // when
    interestRepository.save(interest);

    // then
    assertThat(interest).isNotNull();
    assertThat(interest.getUsers()).isEmpty();
    assertThat(interest.getUsers()).isEqualTo(Set.of());
  }

  @Test
  public void findAllByValueInTest() throws Exception {
    // given
    Set<Interest> interests = new HashSet<>();
    interests.add(buildInterest("과학"));
    interests.add(buildInterest("make the world a better place"));
    interests.add(buildInterest("통계"));
    interestRepository.saveAll(interests);
    List<String> interestsKeywords = interests.stream().map(Interest::getValue).collect(Collectors.toList());

    // when
    List<Interest> resultList = interestRepository.findAllByValueIn(interestsKeywords);
    Set<Interest> results = new HashSet<>(resultList);

    // then
    assertThat(results).isEqualTo(interests);
  }



}