package social.alone.server.push

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import social.alone.server.user.User
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "fcm_token", uniqueConstraints = [UniqueConstraint(columnNames = ["value"])])
class FcmToken(
        @Column val value: String,
        @ManyToOne var user: User? = null
) {

    @Id
    @GeneratedValue
    val id: Long? = null

    @CreationTimestamp
    private val createdAt: LocalDateTime = LocalDateTime.now()

    @UpdateTimestamp
    private var updatedAt: LocalDateTime = LocalDateTime.now()


    fun updateUser(user: User?){
        this.user = user;
    }

}
