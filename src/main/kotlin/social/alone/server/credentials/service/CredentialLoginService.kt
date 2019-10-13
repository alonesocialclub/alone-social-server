package social.alone.server.credentials.service

import org.springframework.stereotype.Service
import social.alone.server.credentials.domain.Credential
import social.alone.server.credentials.dto.AppleLoginDto
import social.alone.server.credentials.repository.CredentialRepository
import social.alone.server.user.domain.User
import java.util.*

@Service
class CredentialLoginService(private val credentialRepository: CredentialRepository) {
    fun getUser(appleLoginDto: AppleLoginDto): Credential?{
        val credential = credentialRepository.findByIdentifier(appleLoginDto.userIdentifier)
        if (!credential.isPresent) {
            return null
        }
        return credential.get()
    }
}