package social.alone.server.interest;


import social.alone.server.BaseRepositoryTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class InterestRepositoryTest extends BaseRepositoryTest {


  @Test
  public void saveTest() throws Exception {

    // given
    Interest interest = new Interest("스타트업");
    // when
    interestRepository.save(interest);

    // then
    assertThat(interest).isNotNull();
    Assertions.assertThat(interest.getUsers()).isEmpty();
  }

  @Test
  public void findAllByValueInTest() throws Exception {
    // given
    List<Interest> interests = new ArrayList<>();
    interests.add(buildInterest("sc"));
    interests.add(buildInterest("make the world a better place"));
    interests.add(buildInterest("statistics"));
    interestRepository.saveAll(interests);
    List<String> interestsKeywords = interests.stream().map(Interest::getValue).collect(Collectors.toList());

    // when
    HashSet<Interest> results = interestRepository.findAllByValueIn(interestsKeywords);

    // then
    assertThat(results).containsAll(interests);
  }


}