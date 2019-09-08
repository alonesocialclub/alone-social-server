package social.alone.server.interest

import org.springframework.data.jpa.repository.JpaRepository

import java.util.HashSet

interface InterestRepository : JpaRepository<Interest, Long> {

    fun findAllByValueIn(values: Set<String>): HashSet<Interest>

}
