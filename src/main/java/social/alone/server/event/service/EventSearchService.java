package social.alone.server.event.service;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class EventSearchService {

  private final EventRepository eventRepository;

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
