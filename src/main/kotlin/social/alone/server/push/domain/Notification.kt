package social.alone.server.push.domain

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
    val id: Long? = null

    @field:CreationTimestamp
    lateinit var createdAt: LocalDateTime


    @field:UpdateTimestamp
    lateinit var updatedAt: LocalDateTime

}
