package social.alone.server.push

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import social.alone.server.push.domain.FcmToken
import social.alone.server.push.infra.FcmTokenRepository
import social.alone.server.user.domain.User

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
