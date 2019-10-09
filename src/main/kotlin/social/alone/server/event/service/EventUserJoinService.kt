package social.alone.server.event.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import social.alone.server.event.domain.Event
import social.alone.server.event.repository.EventRepository
import social.alone.server.user.repository.UserRepository
import java.util.function.Supplier


@Service
@Transactional
class EventUserJoinService(
        val eventRepository: EventRepository,
        val userRepository: UserRepository
) {


    fun joinEvent(eventId: Long, userId: String): Event {
        val user = userRepository.findById(userId).get()
        val event = eventRepository.findById(eventId).get()
        event.joinEvent(user)
        return event
    }

    fun joinEventCancel(eventId: Long?, userId: String?): Event {
        val event = this.eventRepository.findById(eventId!!).orElseThrow<RuntimeException>(Supplier<RuntimeException> { RuntimeException() })
        val user = this.userRepository.findById(userId!!).orElseThrow<RuntimeException>(Supplier<RuntimeException> { RuntimeException() })
        event.joinCancelEvent(user)
        return this.eventRepository.save(event)
    }
}
