package social.alone.server.push


import com.google.firebase.messaging.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import social.alone.server.event.domain.Event
import social.alone.server.push.domain.FcmToken
import social.alone.server.push.infra.FcmTokenRepository

@Service
class NotificationSendSvc {

    @Autowired
    lateinit var fcmTokenRepository: FcmTokenRepository

    fun afterEventCreation(event: Event) {
        var list = fcmTokenRepository.findAll(PageRequest.of(0, 100, Sort.by(Sort.Direction.DESC, "id")))
    }

    fun send(
            notification: social.alone.server.push.domain.Notification,
            fcmToken: FcmToken
    ) {
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
}
