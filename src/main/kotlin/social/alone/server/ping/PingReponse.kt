package social.alone.server.ping

import social.alone.server.user.domain.User
import java.time.LocalDateTime


data class PingView(
        val sender: User,
        val receiver: User,
        val postId: String,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime
)

fun Ping.view() = PingView(
        sender = sender,
        receiver = receiver,
        postId = post.id,
        createdAt = createdAt,
        updatedAt = updatedAt
)