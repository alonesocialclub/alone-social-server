package social.alone.server.push

import social.alone.server.user.User
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

    init {
        this.user = userMaybe.orElse(null)
    }

}
