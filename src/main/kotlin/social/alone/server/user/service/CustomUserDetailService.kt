package social.alone.server.user.service


import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import social.alone.server.auth.oauth2.user.UserPrincipalAdapter
import social.alone.server.exception.ResourceNotFoundException
import social.alone.server.user.repository.UserRepository

@Service
@Transactional
class CustomUserDetailService (
        val userRepository: UserRepository
): UserDetailsService {


    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email)
                .orElseThrow { UsernameNotFoundException("User not found with email : $email") }

        return UserPrincipalAdapter.create(user)
    }

    fun loadUserById(id: String): UserDetails {
        val user = userRepository.findById(id).orElseThrow { ResourceNotFoundException("User", "id", id) }

        return UserPrincipalAdapter.create(user)
    }
}