package social.alone.server.config;


import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class QuerydslConfigTest {

  @Autowired
  JPAQueryFactory jpaQueryFactory;

  @Test
  public void di() {
    assertThat(jpaQueryFactory).isNotNull();
  }
}