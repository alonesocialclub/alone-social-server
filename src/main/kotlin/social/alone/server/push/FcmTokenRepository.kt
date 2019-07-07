package social.alone.server.push

import org.springframework.data.jpa.repository.JpaRepository
import social.alone.server.push.domain.FcmToken


interface FcmTokenRepository : JpaRepository<FcmToken, Long> {
    fun findByValue(value: String): FcmToken?
}