package social.alone.server.push

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import social.alone.server.user.User
import java.util.*

@Service
class FcmTokenRegisterSvc(@Autowired private val repository: FcmTokenRepository) {

    internal fun register(token: String, user: Optional<User>): FcmToken {
        return repository.save(FcmToken(token, user))
    }
}
