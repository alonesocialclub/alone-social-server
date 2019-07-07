package social.alone.server.push

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import social.alone.server.user.User

@Service
class FcmTokenRegisterSvc(@Autowired private val repository: FcmTokenRepository) {

    internal fun register(token: String, user: User?): FcmToken {
        repository.findByValue(token)?.let {
            it.updateUser(user)
            return it
        }
        return repository.save(FcmToken(token, user))
    }
}
