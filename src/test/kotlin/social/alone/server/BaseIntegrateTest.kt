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
import social.alone.server.event.domain.Event
import social.alone.server.event.dto.EventDto
import social.alone.server.event.repository.EventRepository
import social.alone.server.event.type.EventType
import social.alone.server.event.type.EventTypeDto
import social.alone.server.event.type.EventTypeRepository
import social.alone.server.location.Location
import social.alone.server.location.LocationDto
import social.alone.server.user.domain.User
import social.alone.server.user.repository.UserRepository
import java.time.LocalDateTime
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
    lateinit var eventRepository: EventRepository

    @Autowired
    lateinit protected var userRepository: UserRepository

    @Autowired
    lateinit protected var eventTypeRepository: EventTypeRepository

    @BeforeTransaction
    fun setUp() {
        this.createdUser = createUser(CREATED_USER_EMAIL)
    }

    @AfterTransaction
    fun setDown() {
        this.userRepository.delete(this.createdUser)
    }


    private fun createUser(email: String): User {
        val user = User(email, "1234", "local")
        return this.userRepository.save(user)
    }


    @Throws(Exception::class)
    protected fun createUserAndBuildAuthToken(): String {
        val random = UUID.randomUUID().toString()
        val user = createUser(random)
        val token = tokenProvider.createToken(user)
        return "Bearer $token"
    }


    protected fun createEvent(user: User): Event {
        val startedAt = LocalDateTime.now().plusDays(3)
        val endedAt = LocalDateTime.now().plusDays(6)
        val location = LocationDto(
                "서울 서초구 강남대로61길 3",
                "스타벅스",
                127.026503385182,
                37.4991561765984,
                "http://place.map.daum.net/27290899")
        val eventTypes = HashSet<EventTypeDto>()
        val eventDto = EventDto(
                "낙성대 주말 코딩",
                location,
                startedAt,
                endedAt,
                eventTypes
        )
        val event = Event(eventDto, user)
        event.updateLocation(location.buildLocation())
        return this.eventRepository.save(event)
    }

    protected fun createEvent(location: Location): Event {
        val event = createEvent()
        event.updateLocation(location)
        return this.eventRepository.save(event)
    }

    @JvmOverloads
    protected fun createEvent(startedAt: LocalDateTime = LocalDateTime.now().plusDays(3), endedAt: LocalDateTime = LocalDateTime.now().plusDays(6)): Event {
        val location = LocationDto(
                "서울 서초구 강남대로61길 3",
                "스타벅스",
                127.026503385182,
                37.4991561765984,
                "http://place.map.daum.net/27290899")
        val next = atomicInteger.incrementAndGet()
        val eventTypes = HashSet<EventTypeDto>()
        val eventDto = EventDto(
                "낙성대 주말 코딩$next",
                LocationDto(
                        "서울 서초구 강남대로61길 3",
                        "스타벅스",
                        127.026503385182,
                        37.4991561765984,
                        "http://place.map.daum.net/27290899"),
                startedAt,
                endedAt,
                eventTypes
        )
        val event = Event(eventDto, this.createdUser)
        event.updateLocation(location.buildLocation())
        return this.eventRepository.save(event)
    }


    protected fun createEventType(value: String): EventType {
        val eventType = EventType.of(value)
        return eventTypeRepository.save(eventType)
    }

    companion object {
        public const val CREATED_USER_EMAIL = "createdUser-me@gmail.com"

        private val atomicInteger = AtomicInteger(0)
    }
}
