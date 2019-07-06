package social.alone.server.push

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import social.alone.server.user.User
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
class FcmToken(
        @Column val value: String, userMaybe: Optional<User>
) {

    @Id
    @GeneratedValue
    val id: Long? = null

    @ManyToOne
    var user: User? = null

    @CreationTimestamp
    private val createdAt: LocalDateTime = LocalDateTime.now();

    @UpdateTimestamp
    private var updatedAt: LocalDateTime = LocalDateTime.now();

    init {
        this.user = userMaybe.orElse(null)
    }

}
