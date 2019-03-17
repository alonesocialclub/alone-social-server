package social.alone.server.event.repository;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventRepositoryImplTest {
  @Autowired
  EventRepository eventRepository;

  @Test
  @Ignore
  public void test() {
//    var results = eventRepository.search();
//    assertThat(results).isNotNull();
  }
}