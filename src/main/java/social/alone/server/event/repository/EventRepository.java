package social.alone.server.event.repository;

import social.alone.server.event.Event;
import social.alone.server.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryCustom {

  Page<Event> findByOwner(User user, Pageable pageable);

  Page<Event> findByUsersContaining(User user, Pageable pageable);
}
