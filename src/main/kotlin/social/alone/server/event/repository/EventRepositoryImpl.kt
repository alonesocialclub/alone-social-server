package social.alone.server.event.repository

import com.querydsl.jpa.JPQLQuery
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import social.alone.server.event.domain.Event
import social.alone.server.event.domain.QEvent
import social.alone.server.event.type.EventQueryParams
import java.time.LocalDateTime

class EventRepositoryImpl : QuerydslRepositorySupport(Event::class.java), EventRepositoryCustom {

    override fun search(pageable: Pageable, eventQueryParams: EventQueryParams): PageImpl<Event> {
        val event = QEvent.event
        var query = from<Event>(event)

        query = filterEndEvent(query)
        query = sortByCoordinate(eventQueryParams, query, event)

        val events = query
                .offset(pageable.offset)
                .limit(pageable.pageSize.toLong())
                .fetch()
        return PageImpl(events, pageable, query.fetchCount())
    }

    private fun filterEndEvent(query: JPQLQuery<Event>): JPQLQuery<Event> {
        var query1 = query
        query1 = query1.where(QEvent.event.startedAt.after(LocalDateTime.now()))
        return query1
    }

    private fun sortByCoordinate(eventQueryParams: EventQueryParams, query: JPQLQuery<Event>, event: QEvent): JPQLQuery<Event> {
        var query1 = query
        val coordinate = eventQueryParams.coordinate
        if (coordinate.isPresent) {
            val exactCoordinate = coordinate.get()
            query1 = query1.orderBy(
                    event.location.longitude.subtract(exactCoordinate.longitude).abs()
                            .add(
                                    event.location.latitude.subtract(exactCoordinate.latitude).abs())
                            .asc()
            )
        }
        return query1
    }


}
