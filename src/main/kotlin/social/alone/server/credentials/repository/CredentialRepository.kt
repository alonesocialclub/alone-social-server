package social.alone.server.credentials.repository

import org.springframework.data.jpa.repository.JpaRepository
import social.alone.server.credentials.domain.Credential

interface CredentialRepository : JpaRepository<Credential, String>
