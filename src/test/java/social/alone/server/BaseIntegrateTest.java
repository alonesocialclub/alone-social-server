package social.alone.server;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import social.alone.server.auth.email.SignUpRequestDto;
import social.alone.server.event.Event;
import social.alone.server.event.dto.EventDto;
import social.alone.server.event.repository.EventRepository;
import social.alone.server.event.type.EventType;
import social.alone.server.event.type.EventTypeRepository;
import social.alone.server.link.Link;
import social.alone.server.link.LinkRepository;
import social.alone.server.location.Location;
import social.alone.server.location.LocationDto;
import social.alone.server.user.User;
import social.alone.server.user.UserRepository;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "server.money-whip.com", uriPort = 443)
@Import(RestDocsConfiguration.class)
@ActiveProfiles("test")
@Ignore
@Transactional
public class BaseIntegrateTest {
    protected final static String CREATED_USER_EMAIL = "createdUser-me@gmail.com";
    protected User createdUser;

    private static final AtomicInteger atomicInteger = new AtomicInteger(0);

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected ModelMapper modelMapper;

    @Autowired
    protected EventRepository eventRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected EventTypeRepository eventTypeRepository;

    @Autowired
    protected LinkRepository linkRepository;

    @BeforeTransaction
    public void setUp() {
        this.createdUser = createUser(CREATED_USER_EMAIL);
    }

    @AfterTransaction
    public void setDown() {
        this.userRepository.delete(this.createdUser);
    }


    private User createUser(String email) {
        User user = new User(email, "1234", "local");
        return this.userRepository.save(user);
    }


    protected String buildAuthToken() throws Exception {

        var next = atomicInteger.incrementAndGet();

        SignUpRequestDto data = SignUpRequestDto.builder()
                .email(next + "@test.com")
                .password("12345678")
                .name("Jeff")
                .build();

        var perform = mockMvc.perform(
                post("/api/auth/signup/email")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(data))
        ).andExpect(status().isCreated());

        String response = perform.andReturn().getResponse().getContentAsString();
        String token = JsonPath.parse(response).read("token").toString();

        return "Bearer " + token;
    }


    protected Event createEvent(User user){
        LocalDateTime startedAt = LocalDateTime.now().plusDays(3);
        LocalDateTime endedAt = LocalDateTime.now().plusDays(6);
        LocationDto location = new LocationDto(
                "서울 서초구 강남대로61길 3",
                "스타벅스",
                127.026503385182,
                37.4991561765984,
                "http://place.map.daum.net/27290899");
        var next = atomicInteger.incrementAndGet();
        EventDto eventDto = EventDto.builder()
                .name("event" + next)
                .description("Rest")
                .startedAt(startedAt)
                .endedAt(endedAt)
                .limitOfEnrollment(5)
                .location(location)
                .build();
        Event event = new Event(eventDto, user);
        event.updateLocation(location.buildLocation());
        return this.eventRepository.save(event);
    }


    protected Event createEvent() {
        return createEvent(LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(6));
    }

    protected Event createEvent(Location location) {
        var event = createEvent();
        event.updateLocation(location);
        return this.eventRepository.save(event);
    }

    protected Event createEvent(LocalDateTime startedAt, LocalDateTime endedAt) {
        LocationDto location = new LocationDto(
                "서울 서초구 강남대로61길 3",
                "스타벅스",
                127.026503385182,
                37.4991561765984,
                "http://place.map.daum.net/27290899");
        var next = atomicInteger.incrementAndGet();
        EventDto eventDto = EventDto.builder()
                .name("event" + next)
                .description("Rest")
                .startedAt(startedAt)
                .endedAt(endedAt)
                .limitOfEnrollment(5)
                .location(location)
                .build();
        Event event = new Event(eventDto, this.createdUser);
        event.updateLocation(location.buildLocation());
        return this.eventRepository.save(event);
    }

    protected Link createLink() {
        Event event = createEvent();
        Link link = event.createLink();
        return this.linkRepository.save(link);
    }

    protected EventType createEventType(String value) {
        EventType eventType = EventType.of(value);
        return eventTypeRepository.save(eventType);
    }
}
