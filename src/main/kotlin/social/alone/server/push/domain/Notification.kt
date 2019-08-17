package social.alone.server.push.domain

import com.google.firebase.messaging.*
import com.google.firebase.messaging.Notification
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import social.alone.server.user.domain.User
import java.time.LocalDateTime
import javax.persistence.*



@Entity
@Table(name = "notification")
class Notification(
        @Column val title: String,
        @Column val body: String,
        @Column val link: String = ""
) {

    @Id
    @GeneratedValue
    @Column
    val id: Long? = null

    @field:CreationTimestamp
    @Column
    lateinit var createdAt: LocalDateTime


    @field:UpdateTimestamp
    @Column
    lateinit var updatedAt: LocalDateTime


    fun toMessage(fcmToken: FcmToken): Message? {
        return Message.builder()
                .setNotification(Notification(title, body))
                .setToken(fcmToken.value)
                .setAndroidConfig(
                        AndroidConfig
                                .builder()
                                .setNotification(AndroidNotification.builder().setSound("default").build())
                                .putData("link", link)
                                .build()
                )
                .setApnsConfig(
                        ApnsConfig
                                .builder()
                                .setAps(
                                        Aps
                                                .builder()
                                                .setSound("default")
                                                .putCustomData("link", link)
                                                .build()
                                )
                                .build()
                )
                .build()
    }

}
