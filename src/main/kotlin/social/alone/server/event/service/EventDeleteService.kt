package social.alone.server.event.service

import org.springframework.stereotype.Service
import social.alone.server.event.domain.Event
import social.alone.server.event.repository.EventRepository
import java.util.function.Consumer

@Service
class EventDeleteService(private val eventRepository: EventRepository) {

    fun delete(eventId: Long?) {
        val event = this.eventRepository!!.findById(eventId!!)
        event.ifPresent(Consumer<Event> { this.eventRepository.delete(it) })
    }
}
