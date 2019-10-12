package social.alone.server


import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Ignore
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.transaction.AfterTransaction
import org.springframework.test.context.transaction.BeforeTransaction
import org.springframework.test.web.servlet.MockMvc
import org.springframework.transaction.annotation.Transactional
import social.alone.server.auth.oauth2.user.TokenProvider
import social.alone.server.pickture.Picture
import social.alone.server.pickture.PictureRepository
import social.alone.server.user.domain.User
import social.alone.server.user.repository.UserRepository
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "server.money-whip.com", uriPort = 443)
@Import(RestDocsConfiguration::class)
@ActiveProfiles("test")
@Ignore
@Transactional
class BaseIntegrateTest {
    protected lateinit var createdUser: User

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var tokenProvider: TokenProvider

    @Autowired
    lateinit protected var userRepository: UserRepository

    @Autowired
    lateinit protected var pictureRepository: PictureRepository

    @BeforeTransaction
    fun setUp() {
        this.createdUser = createUser(CREATED_USER_EMAIL)
    }

    @AfterTransaction
    fun setDown() {
        this.userRepository.delete(this.createdUser)
    }

    protected fun createImage(): Picture{
        val image = Picture("https://test-picture.png")
        return pictureRepository.save(image)
    }


    private fun createUser(email: String): User {
        val user = makeUser(email)
        return this.userRepository.save(user)
    }


    @Throws(Exception::class)
    protected fun createUserAndBuildAuthToken(): String {
        val random = UUID.randomUUID().toString() + "@email.com"
        val user = createUser(random)
        val token = tokenProvider.createToken(user)
        return "Bearer $token"
    }


    companion object {
        const val CREATED_USER_EMAIL = "createdUser-me@gmail.com"
    }
}
