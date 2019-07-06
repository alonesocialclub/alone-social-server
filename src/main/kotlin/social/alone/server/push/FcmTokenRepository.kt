package social.alone.server.push

import org.springframework.data.jpa.repository.JpaRepository


interface FcmTokenRepository : JpaRepository<FcmToken, Long>
