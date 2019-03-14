package social.alone.server.event.type;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventTypeRepository extends JpaRepository<EventType, Long> {
  List<EventType> findAllByValueIn(List<String> values);
}
