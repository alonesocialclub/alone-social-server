package social.alone.server.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface LocationRepository
        extends JpaRepository<Location, Long>, QuerydslPredicateExecutor<Location> {

  Optional<Location> findByLongitudeAndLatitudeAndName(double longitude, double latitude, String name);
}
