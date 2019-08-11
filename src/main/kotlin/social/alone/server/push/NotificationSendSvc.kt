package social.alone.server.push


import com.google.firebase.messaging.*
import org.springframework.core.env.Environment
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import social.alone.server.event.domain.Event
import social.alone.server.push.domain.FcmToken
import social.alone.server.push.infra.FcmTokenRepository
import social.alone.server.user.domain.User
import java.util.*

@Service
class NotificationSendSvc(
        val env: Environment,
        val fcmTokenRepository: FcmTokenRepository
) {

    @Async
    fun afterEventCreation(event: Event) {
        var notification = social.alone.server.push.domain.Notification(
                "알림", "새로운 이벤트가 생성되었습니다.", "alsc://events/" + event.id
        )
        fcmTokenRepository
                .findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id")))
                .map { item ->
                    {
                        send(notification, item)
                    }
                }
    }

    fun send(
            notification: social.alone.server.push.domain.Notification,
            fcmToken: FcmToken
    ) {
        if (!Arrays.asList(*env.activeProfiles).contains("prod")) {
            return
        }
        // See documentation on defining a message payload.
        val message = messageBuilder(notification, fcmToken)

        try {
            val response = FirebaseMessaging.getInstance().send(message)
            println(response)
        } catch (e: FirebaseMessagingException) {
            e.printStackTrace()
        }

    }

    private fun messageBuilder(notification: social.alone.server.push.domain.Notification, fcmToken: FcmToken): Message? {
        val message = Message.builder()
                .setNotification(Notification(notification.title, notification.body))
                .setToken(fcmToken.value)
                .setAndroidConfig(
                        AndroidConfig
                                .builder()
                                .setNotification(AndroidNotification.builder().setSound("default").build())
                                .putData("link", notification.link)
                                .build()
                )
                .setApnsConfig(
                        ApnsConfig
                                .builder()
                                .setAps(
                                        Aps
                                                .builder()
                                                .setSound("default")
                                                .putCustomData("link", notification.link)
                                                .build()
                                )
                                .build()
                )
                .build()
        return message
    }

    @Async
    fun afterEventJoin(event: Event, user: User) {
        var notification = social.alone.server.push.domain.Notification(
                "알림", "새로운 참가자가 있습니다.", "alsc://events/" + event.id
        )
        // TODO implement
        event.users.forEach { it ->
            {
                if (it != user) {
                    println(it.name)
                }
            }
        }
    }
}
