package social.alone.server.event.domain

import lombok.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.domain.AbstractAggregateRoot
import social.alone.server.event.dto.EventDto
import social.alone.server.event.type.EventType
import social.alone.server.link.Link
import social.alone.server.location.Location
import social.alone.server.slack.SlackMessagable
import social.alone.server.slack.SlackMessageEvent
import social.alone.server.user.domain.User
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.persistence.*


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = ["id"], callSuper = false)
@ToString(of = ["name", "startedAt"])
class Event : AbstractAggregateRoot<Event>, SlackMessagable {

    @Id
    @GeneratedValue
    var id: Long? = null

    var name: String? = null

    var description: String? = null

    lateinit var startedAt: LocalDateTime

    lateinit var endedAt: LocalDateTime

    var limitOfEnrollment: Int = 0

    @CreationTimestamp
    var createdAt: LocalDateTime? = null

    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null
        protected set

    @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinColumn(name = "location_id")
    lateinit var location: Location

    @ManyToOne
    var owner: User

    @ManyToMany(cascade = [CascadeType.PERSIST])
    @JoinTable(name = "event_event_type", joinColumns = [JoinColumn(name = "event_id")], inverseJoinColumns = [JoinColumn(name = "event_type_id")])
    @Setter
    var eventTypes: MutableSet<EventType> = HashSet()

    @ManyToMany
    @JoinTable(name = "event_user", joinColumns = [JoinColumn(name = "event_id")], inverseJoinColumns = [JoinColumn(name = "user_id")])
    var users: MutableSet<User> = HashSet()

    val linkHtml: String
        get() {
            val url = "https://alone.social/events/" + this.id!!
            val formatter = DateTimeFormatter.ofPattern("MM/dd hh")
            val description = this.startedAt!!.format(formatter) + "시 " + this.location.name + "에서"

            return String.format(
                    "<html>" +
                            "<head>" +
                            "<title>%s</title>" +
                            "<meta property=\"og:title\" content=\"각자 할 일 해요! 같이\"/>" +
                            "<meta property=\"og:description\" content=\"%s\"/>" +
                            "<meta property=\"og:image\" content=\"%s\" />" +
                            "<script>window.location.replace(\'%s\');</script>" +
                            "</head>" +
                            "</html>",
                    name,
                    description,
                    location.imageUrl,
                    url
            )
        }

    constructor(eventDto: EventDto, user: User, location: Location) {
        this.updateByEventDto(eventDto)
        this.owner = user
        this.location = location
        this.activityLogEventCreate()
    }

    constructor(eventDto: EventDto, user: User) {
        this.owner = user
        this.updateByEventDto(eventDto)
        this.activityLogEventCreate()
    }

    fun updateByEventDto(eventDto: EventDto) {
        this.name = eventDto.name
        this.description = eventDto.description
        this.startedAt = eventDto.startedAt
        this.endedAt = eventDto.endedAt
        this.limitOfEnrollment = eventDto.limitOfEnrollment
    }

    fun joinEvent(user: User) {
        if (this.owner == user) {
            return
        }
        this.activityLogJoinEvent(user)
        this.users.add(user)
    }

    fun joinCancelEvent(user: User) {
        if (this.owner == user) {
            return
        }
        this.activityLogJoinEventCancel(user)
        this.users.remove(user)
    }

    fun createLink(): Link {
        return Link(this)
    }

    fun updateLocation(location: Location) {
        this.location = location
    }

    fun activityLogEventCreate() {
        if (!owner.isAdmin) {
            val message = this.owner.toString() + "님이 " + this.toString() + "를 생성했습니다."
            this.registerEvent(buildSlackMessageEvent(message))
        }
    }

    fun activityLogJoinEvent(user: User) {
        val message = user.name + "님이 " + this.toString() + "를 에 참가 신청을 하셨습니다."
        this.registerEvent(buildSlackMessageEvent(message))
    }

    fun activityLogJoinEventCancel(user: User) {
        val message = user.name + "님이 " + this.toString() + "를 에 참가 신청을 취소 하셨습니다."
        this.registerEvent(buildSlackMessageEvent(message))
    }

    override fun buildSlackMessageEvent(message: String): SlackMessageEvent {
        return SlackMessageEvent(this, message)
    }
}
