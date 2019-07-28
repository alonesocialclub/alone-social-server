package social.alone.server.event.type

import org.springframework.data.jpa.repository.JpaRepository

interface EventTypeRepository : JpaRepository<EventType, Long> {
    fun findAllByValueIn(values: List<String>): List<EventType>
}
