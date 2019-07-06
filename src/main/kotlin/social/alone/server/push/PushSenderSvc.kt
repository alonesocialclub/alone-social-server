package social.alone.server.push


import com.google.firebase.messaging.*
import org.springframework.stereotype.Service

@Service
class PushSenderSvc {

    fun send(fcmToken: String) {
        // See documentation on defining a message payload.
        val message = Message.builder()
                .setApnsConfig(
                        ApnsConfig
                                .builder()
                                .setAps(
                                        Aps
                                                .builder()
                                                .setAlert(ApsAlert.builder().setBody("body").build())
                                                .build()
                                )
                                .build()
                )
                .setNotification(Notification("title~!!", "body~"))
                .setToken(fcmToken)
                .build()


        try {
            val response = FirebaseMessaging.getInstance().send(message)
        } catch (e: FirebaseMessagingException) {
            e.printStackTrace()
        }

    }
}
