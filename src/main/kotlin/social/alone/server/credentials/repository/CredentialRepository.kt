package social.alone.server.credentials.repository

import org.springframework.data.jpa.repository.JpaRepository
import social.alone.server.credentials.domain.Credential
import java.util.*

interface CredentialRepository : JpaRepository<Credential, String>{
    fun findByIdentifier(identifier: String): Optional<Credential>
}
