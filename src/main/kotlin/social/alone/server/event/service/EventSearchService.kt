package social.alone.server.event.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import social.alone.server.event.domain.Event
import social.alone.server.event.domain.QEvent
import social.alone.server.event.repository.EventRepository
import social.alone.server.event.type.EventQueryParams
import social.alone.server.user.domain.User

@Service
@Transactional(readOnly = true)
class EventSearchService(var eventRepository: EventRepository) {

    fun findAllBy(
            pageable: Pageable,
            eventQueryParams: EventQueryParams
    ): Page<Event> {
        return eventRepository.search(
                pageable,
                eventQueryParams
        )
    }

    fun findAllMyUpcomingEvents(
            user: User,
            pageable: Pageable
    ): Page<Event> {

        return eventRepository.findAll(
                filterParticipatingEvent(user), pageable
        )
    }

    fun findAllMyPastEvents(
            user: User,
            pageable: Pageable
    ): Page<Event> {
        return eventRepository.findAll(
                filterParticipatingEvent(user), pageable
        )
    }

    private fun filterParticipatingEvent(user: User) = QEvent.event.owner.eq(user)
            .or(QEvent.event.users.contains(user))

}
