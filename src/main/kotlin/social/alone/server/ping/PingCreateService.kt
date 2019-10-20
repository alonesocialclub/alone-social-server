package social.alone.server.ping

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import social.alone.server.ping.QPing.*
import social.alone.server.post.domain.Post
import social.alone.server.post.domain.QPost
import social.alone.server.post.domain.QPost.*
import social.alone.server.user.domain.QUser
import social.alone.server.user.domain.User

@Service
@Transactional
class PingCreateService(
        private val pingRepository: PingRepository
) {

    fun create(sender: User, receiver: User, post: Post): Ping {

        val existsPing = pingRepository.findAll(
                ping.receiver.eq(receiver)
                        .and(ping.sender.eq(sender))
                        .and(ping.post.eq(post)
                        )
        ).firstOrNull()
        if (existsPing != null) {
            return existsPing
        }
        val ping = Ping(sender, receiver, post)
        return pingRepository.save(ping)
    }
}