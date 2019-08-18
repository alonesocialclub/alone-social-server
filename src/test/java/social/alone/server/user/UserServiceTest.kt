package social.alone.server.user

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Fail.fail
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional
import social.alone.server.interest.InterestDto
import social.alone.server.user.domain.User
import social.alone.server.user.dto.UserDto
import social.alone.server.user.repository.UserRepository
import social.alone.server.user.service.CustomUserDetailService

@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    lateinit var userService: CustomUserDetailService

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun createLocalAuthUserTest() {
        // Given
        val user = build()

        // When
        val userDetailsService = userService
        val userDetails = userDetailsService.loadUserByUsername(user.email!!)

        // Then
        assertThat(userDetails.password).isEqualTo(user.password)
        assertThat(userDetails.username).isEqualTo(user.email)
    }

    @Test
    fun findByUsernameTest_not_found() {
        // Given
        val notFoundUserName = "failfailfailnotexists@fail.com"

        // When
        try {
            val userDetailsService = userService
            userDetailsService!!.loadUserByUsername(notFoundUserName) // username
            fail<Any>("supposed to be failed")
        } catch (e: UsernameNotFoundException) {
            // Then
            assertThat(e.message).contains(notFoundUserName)
        }

    }

    @Test
    fun saveTest__fix_email_only() {
        val user = build()
        val userName = user.name
        val userDto = UserDto(null, "email@email.com", null)

        userService!!.update(user, userDto)

        assertThat(user.email).isEqualTo(userDto.email)
        assertThat(user.name).isEqualTo(userName)
    }

    @Test
    fun saveTest__fix_interests_only() {
        val user = build()
        val userName = user.name
        val interest = InterestDto("foo")
        val userDto = UserDto(
                null,
                null,
                listOf(interest)
        )

        userService.update(user, userDto)

        assertThat(
                setOf(user.interests.map { it.value })
        ).contains(listOf(interest.value))
        assertThat(user.name).isEqualTo(userName)
    }

    private fun build(): User {
        val name = "findByUsername"
        val email = "findByUsername@test.com"
        val password = "test"
        val user = User(email, passwordEncoder.encode(password), name)
        return userRepository.save(user)
    }
}