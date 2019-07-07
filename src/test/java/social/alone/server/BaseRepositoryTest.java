package social.alone.server;

import social.alone.server.interest.Interest;
import social.alone.server.interest.InterestRepository;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@Ignore
@Transactional
public class BaseRepositoryTest {

  @Autowired
  protected InterestRepository interestRepository;

  public Interest buildInterest(String value) {
    return interestRepository.save(
            new Interest(value)
    );
  }

}
