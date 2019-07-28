package social.alone.server.location

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

import java.util.Optional

interface LocationRepository : JpaRepository<Location, Long>, QuerydslPredicateExecutor<Location> {

    fun findByLongitudeAndLatitudeAndName(longitude: Double, latitude: Double, name: String): Optional<Location>
}
