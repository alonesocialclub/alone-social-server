package social.alone.server.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import social.alone.server.user.domain.AuthProvider
import social.alone.server.user.domain.User
import java.util.*

interface UserRepository : JpaRepository<User, String> {

    fun findByEmail(email: String): Optional<User>

    fun findByEmailAndProvider(email: String, provider: AuthProvider): Optional<User>

    fun existsByEmail(email: String): Boolean?

}
