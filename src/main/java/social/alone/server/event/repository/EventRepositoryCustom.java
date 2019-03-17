package social.alone.server.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import social.alone.server.event.Event;
import social.alone.server.event.EventQueryParams;
import social.alone.server.user.User;

public interface EventRepositoryCustom {
  Page<Event> search(Pageable pageable, User user, EventQueryParams eventQueryParams);
  Page<Event> search(Pageable pageable, EventQueryParams eventQueryParams);
}
