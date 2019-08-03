package social.alone.server.push.domain

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import social.alone.server.user.domain.User
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

    @field:CreationTimestamp
    lateinit var createdAt: LocalDateTime


    @field:UpdateTimestamp
    lateinit var updatedAt: LocalDateTime


    fun updateUser(user: User?){
        this.user = user
    }

}
