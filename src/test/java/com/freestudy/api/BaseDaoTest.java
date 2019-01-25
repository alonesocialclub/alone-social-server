package com.freestudy.api;

import com.freestudy.api.interest.Interest;
import com.freestudy.api.interest.InterestRepository;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@Ignore
@Transactional
public class BaseDaoTest {

  @Autowired
  protected InterestRepository interestRepository;

  public Interest buildInterest(String value) {
    return interestRepository.save(
            Interest
                    .builder()
                    .value(value)
                    .build()
    );
  }

}
