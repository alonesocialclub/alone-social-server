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
import social.alone.server.makeUser
import social.alone.server.user.domain.User
import social.alone.server.user.repository.UserRepository
import social.alone.server.user.service.CustomUserDetailService

@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
class CustomUserDetailServiceTest {

    @Autowired
    lateinit var customUserDetailService: CustomUserDetailService

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun createLocalAuthUserTest() {
        // Given
        val user = build()

        // When
        val userDetailsService = customUserDetailService
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
            val userDetailsService = customUserDetailService
            userDetailsService.loadUserByUsername(notFoundUserName) // username
            fail<Any>("supposed to be failed")
        } catch (e: UsernameNotFoundException) {
            // Then
            assertThat(e.message).contains(notFoundUserName)
        }

    }


    private fun build(): User {
        val user = makeUser()
        return userRepository.save(user)
    }
}