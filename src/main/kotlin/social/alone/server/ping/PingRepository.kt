package social.alone.server.ping

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor


interface PingRepository : JpaRepository<Ping, String>, QuerydslPredicateExecutor<Ping>