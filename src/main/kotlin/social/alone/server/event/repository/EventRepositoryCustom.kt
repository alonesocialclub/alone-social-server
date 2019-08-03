package social.alone.server.event.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import social.alone.server.event.domain.Event
import social.alone.server.event.type.EventQueryParams
import social.alone.server.user.domain.User

interface EventRepositoryCustom {
    fun search(pageable: Pageable, user: User?, eventQueryParams: EventQueryParams): Page<Event>
    fun search(pageable: Pageable, eventQueryParams: EventQueryParams): Page<Event>
}
