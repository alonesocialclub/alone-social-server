package social.alone.server.event.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import social.alone.server.event.Event;
import social.alone.server.event.type.EventQueryParams;
import social.alone.server.event.repository.EventRepository;
import social.alone.server.user.User;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EventSearchService {

  private EventRepository eventRepository;


  public EventSearchService(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  public Page<Event> findAllBy(
          Pageable pageable,
          Optional<User> user,
          EventQueryParams eventQueryParams
  ) {
    if (user.isEmpty()){
      return eventRepository.search(pageable, eventQueryParams);
    } else {
      return eventRepository.search(pageable, user.get(), eventQueryParams);
    }
  }
}
