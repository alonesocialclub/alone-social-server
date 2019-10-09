package social.alone.server.push


import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingException
import org.springframework.core.env.Environment
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import social.alone.server.push.domain.FcmToken
import social.alone.server.push.domain.Notification
import social.alone.server.push.infra.FcmTokenRepository
import social.alone.server.user.domain.User
import java.util.*

@Service
@Transactional
class NotificationSendSvc(
        val env: Environment,
        val fcmTokenRepository: FcmTokenRepository
) {



    @Async
    fun noticeAll(body: String) {
        pushAll(Notification(
                "공지", body, "alsc://events"
        ))
    }


    private fun pushAll(notification: Notification) {
        val tokens = fcmTokenRepository.findAll()
        for (token in tokens) {
            send(notification, token)
        }
    }

    private fun send(notification: Notification, user: User) {
        user.fcmTokens.forEach { token -> send(notification, token) }
    }

    private fun send(
            notification: Notification,
            fcmToken: FcmToken
    ) {
        if (!Arrays.asList(*env.activeProfiles).contains("prod")) {
            return
        }
        // See documentation on defining a message payload.
        val message = notification.toMessage(fcmToken)

        try {
            val response = FirebaseMessaging.getInstance().send(message)
            println(response)
        } catch (e: FirebaseMessagingException) {
            e.printStackTrace()
        }

    }
}
