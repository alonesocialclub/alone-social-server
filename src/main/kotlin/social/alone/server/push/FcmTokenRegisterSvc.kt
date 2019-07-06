package social.alone.server.push

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FcmTokenRegisterSvc(@Autowired private val repository: FcmTokenRepository) {

    internal fun register(token: String): FcmToken {
        return repository.save(FcmToken(token))
    }
}
