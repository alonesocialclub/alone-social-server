package social.alone.server.auth.oauth2.user


import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import social.alone.server.BaseIntegrateTest

class TokenProviderTest : BaseIntegrateTest() {

    @Autowired
    override lateinit var tokenProvider: TokenProvider

    @Test
    fun test() {
        val token = tokenProvider.createToken(createdUser)
        assertThat(token).isNotEmpty()
    }

}