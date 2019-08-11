package social.alone.server.event.repository

import social.alone.server.event.domain.Event
import social.alone.server.user.domain.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface EventRepository :
        JpaRepository<Event, Long>,
        QuerydslPredicateExecutor<Event>,
        EventRepositoryCustom
{

    fun findByOwner(user: User, pageable: Pageable): Page<Event>

    fun findByUsersContaining(user: User, pageable: Pageable): Page<Event>
}
