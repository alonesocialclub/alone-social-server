package social.alone.server.user.service


import lombok.RequiredArgsConstructor
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import social.alone.server.auth.oauth2.user.UserPrincipalAdapter
import social.alone.server.exception.ResourceNotFoundException
import social.alone.server.interest.InterestUpsertService
import social.alone.server.user.dto.UserDto
import social.alone.server.user.repository.UserRepository
import social.alone.server.user.domain.User

@Service
@Transactional
@RequiredArgsConstructor
class UserService : UserDetailsService {

    private val userRepository: UserRepository? = null

    private val interestUpsertService: InterestUpsertService? = null


    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository!!.findByEmail(email)
                .orElseThrow { UsernameNotFoundException("User not found with email : $email") }

        return UserPrincipalAdapter.create(user)
    }

    fun loadUserById(id: Long?): UserDetails {
        val user = userRepository!!.findById(id!!).orElseThrow { ResourceNotFoundException("User", "id", id) }

        return UserPrincipalAdapter.create(user)
    }

    // TODO FIX
    fun update(user_: User, userDto: UserDto): User {

        val user = userRepository!!.findById(user_.id!!).get()

        if (userDto.interests != null) {
            val interests = interestUpsertService!!.saveAll(userDto.interests!!)
            user.setInterests(interests)
        }

        user.update(userDto)

        return user
    }
}