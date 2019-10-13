package social.alone.server.credentials.service

import org.springframework.stereotype.Service
import social.alone.server.credentials.domain.Credential
import social.alone.server.credentials.dto.AppleLoginDto
import social.alone.server.credentials.repository.CredentialRepository
import social.alone.server.user.domain.User


@Service
class CredentialCreateService(private val credentialRepository: CredentialRepository) {
    fun create(user: User, appleLoginDto: AppleLoginDto) {
        val credential = Credential("apple-id", appleLoginDto.userIdentifier, user)
        credentialRepository.save(credential)
    }
}