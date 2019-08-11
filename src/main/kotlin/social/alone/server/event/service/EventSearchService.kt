package social.alone.server.event.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import social.alone.server.event.domain.Event
import social.alone.server.event.domain.QEvent
import social.alone.server.event.repository.EventRepository
import social.alone.server.event.type.EventQueryParams
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class EventSearchService(var eventRepository: EventRepository) {

    fun findAllBy(
            pageable: Pageable,
            eventQueryParams: EventQueryParams
    ): Page<Event> {
        return eventRepository.findAll(
                filterEndEvent(),
                pageable
        )
    }

    private fun filterEndEvent() = QEvent.event.startedAt.after(LocalDateTime.now())
}
