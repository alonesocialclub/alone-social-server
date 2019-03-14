package social.alone.server.location;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Integer> {

  Optional<Location> findByLongitudeAndLatitudeAndName(double longitude, double latitude, String name);
}
