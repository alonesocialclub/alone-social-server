package social.alone.server.push

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import social.alone.server.user.User
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class FcmToken(
        @Column val value: String,
        @ManyToOne val user: User? = null
) {

    @Id
    @GeneratedValue
    val id: Long? = null

    @CreationTimestamp
    private val createdAt: LocalDateTime = LocalDateTime.now()

    @UpdateTimestamp
    private var updatedAt: LocalDateTime = LocalDateTime.now()


}
