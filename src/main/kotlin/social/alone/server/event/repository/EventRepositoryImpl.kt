package social.alone.server.event.repository

import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import social.alone.server.event.domain.Event
import social.alone.server.event.domain.QEvent.event
import social.alone.server.event.type.Coordinate
import social.alone.server.event.type.EventQueryParams
import java.time.LocalDateTime

class EventRepositoryImpl : QuerydslRepositorySupport(Event::class.java), EventRepositoryCustom {

    override fun search(pageable: Pageable, eventQueryParams: EventQueryParams): PageImpl<Event> {
        val query = from<Event>(event)
        // TODO Comment out, 개발시 지나간 모임도 보여야 편함
//        query.where(filterEndEvent())

        if (eventQueryParams.searchByCoordinate()){
            query.orderBy(sortIfCoordinate(eventQueryParams.coordinate!!))
        }

        this.querydsl!!.applyPagination(pageable, query)
        query.offset(pageable.offset)
        query.limit(pageable.pageSize.toLong())

        return PageImpl(query.fetch(), pageable, query.fetchCount())
    }

    private fun filterEndEvent(): BooleanExpression? {
        return event.startedAt.after(LocalDateTime.now())
    }

    private fun sortIfCoordinate(coordinate:Coordinate): OrderSpecifier<Double> {
        return (
                event.location.longitude.subtract(coordinate.longitude).abs()
                        .add(
                                event.location.latitude.subtract(coordinate.latitude).abs())
                        .asc()
                )
    }


}
