package social.alone.server.event;

import social.alone.server.event.type.EventQueryType;
import social.alone.server.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EventSearchServiceTest {

  @InjectMocks
  EventSearchService eventSearchService;


  @Mock
  private EventRepository eventRepository;

  @Test
  public void findAllBy() {

    Optional<User> user = Optional.empty();
    var pageable = Pageable.unpaged();
    var eventQueryParams = new EventQueryParams();
    eventQueryParams.setType(EventQueryType.ALL);
    eventQueryParams.setLatitude(127.026503385182);
    eventQueryParams.setLongitude(37.4991561765984);

    given(eventRepository.findAll(eq(pageable))).willReturn(Page.empty());

    Page<Event> results = eventSearchService.findAllBy(pageable, user, eventQueryParams);

    assertThat(results.isEmpty()).isTrue();
    verify(eventRepository, times(1)).findAll(pageable);
  }
}