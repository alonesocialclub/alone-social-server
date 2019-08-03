package social.alone.server.event.repository

import com.querydsl.core.types.Predicate
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import social.alone.server.event.domain.Event
import social.alone.server.event.domain.QEvent
import social.alone.server.event.type.EventQueryParams
import social.alone.server.event.type.EventQueryType
import social.alone.server.user.domain.User

class EventRepositoryImpl : QuerydslRepositorySupport(Event::class.java), EventRepositoryCustom{

    override fun search(pageable: Pageable, user: User?, eventQueryParams: EventQueryParams): PageImpl<Event> {
        val event = QEvent.event
        var query = from<Event>(event)
        when (eventQueryParams.type) {
            EventQueryType.OWNER -> if (user != null) {
//                query = query.where(event.owner.eq(user))
            }
            EventQueryType.JOINER -> if (user != null) {
                query = query.where(event.users.contains(user))
            }
            EventQueryType.ALL -> {
            }
            else -> throw IllegalArgumentException("eventQueryParams.getType() can not be null")
        }

        // TODO Extract
        val coordinate = eventQueryParams.coordinate
        if (coordinate.isPresent) {
            val exactCoordinate = coordinate.get()
            query = query.orderBy(
                    event.location.longitude.subtract(exactCoordinate.longitude).abs()
                            .add(
                                    event.location.latitude.subtract(exactCoordinate.latitude).abs())
                            .asc()
            )
        }
        query = query.where(conditionalStartedAt(pageable, eventQueryParams))


        val events = query
                .offset(pageable.offset)
                .limit(pageable.pageSize.toLong())
                .fetch()
        return PageImpl(events, pageable, query.fetchCount())
    }

    override fun search(pageable: Pageable, eventQueryParams: EventQueryParams): Page<Event> {
        return search(pageable, null, eventQueryParams)
    }

    private fun conditionalStartedAt(pageable: Pageable, params: EventQueryParams): Predicate? {
        return if (params.startedAt == null) {
            null
        } else null
    }
}
