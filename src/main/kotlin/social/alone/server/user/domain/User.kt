package social.alone.server.user.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.Builder
import lombok.Setter
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.domain.AbstractAggregateRoot
import social.alone.server.auth.oauth2.user.OAuth2UserInfo
import social.alone.server.event.domain.Event
import social.alone.server.interest.Interest
import social.alone.server.push.domain.FcmToken
import social.alone.server.slack.SlackMessagable
import social.alone.server.slack.SlackMessageEvent
import social.alone.server.user.dto.UserDto
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(columnNames = ["email"])])
data class User(@Id @GeneratedValue var id: Long? = null) : AbstractAggregateRoot<User>(), SlackMessagable {

    @CreationTimestamp
    val createdAt: LocalDateTime? = null

    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null
        protected set

    @NotNull
    @Column(nullable = false)
    @Setter
    var name: String? = null

    @NotNull
    @Email
    @Column(nullable = false)
    @Setter
    @JsonIgnore
    var email: String? = null

    @Setter
    var imageUrl: String? = null

    @JsonIgnore
    var password: String? = null

    @NotNull
    @Enumerated(EnumType.STRING)
    var provider: AuthProvider? = null

    @JsonIgnore
    var providerId: String? = null

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    val roles: MutableSet<UserRole> = HashSet()


    @ManyToMany(cascade = [CascadeType.PERSIST], fetch = FetchType.EAGER)
    @JoinTable(name = "user_interest", joinColumns = [JoinColumn(name = "user_id")], inverseJoinColumns = [JoinColumn(name = "interest_id")])
    var interests: MutableSet<Interest> = HashSet()

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    val events: MutableSet<Event> = HashSet();

    @OneToMany
    var fcmTokens: MutableSet<FcmToken> = HashSet()

    val isAdmin: Boolean
        get() = this.roles.contains(UserRole.ADMIN)

    @Builder
    constructor(email: String, password: String, name: String) : this() {
        this.roles.add(UserRole.USER)
        this.interests = HashSet()

        this.email = email
        this.password = password
        this.name = name
        this.provider = AuthProvider.local
        this.sendSlackActivityMsg()
    }

    constructor(oAuth2UserInfo: OAuth2UserInfo, provider: AuthProvider) : this() {
        this.roles.add(UserRole.USER)
        this.interests = HashSet()

        this.name = oAuth2UserInfo.name
        this.email = oAuth2UserInfo.email
        this.imageUrl = oAuth2UserInfo.imageUrl
        this.provider = provider
        this.providerId = oAuth2UserInfo.id
        this.sendSlackActivityMsg()
    }

    fun setInterests(interests: HashSet<Interest>) {
        this.interests = interests
    }

    fun update(userDto: UserDto) {
        if (userDto.email != null) {
            this.email = userDto.email
        }
        if (userDto.name != null) {
            this.name = userDto.name
        }
    }

    private fun sendSlackActivityMsg() {
        val message = name + "님이 " + provider + "를 통해 가입하셨습니다."
        this.registerEvent(buildSlackMessageEvent(message))
    }

    override fun buildSlackMessageEvent(message: String): SlackMessageEvent {
        return SlackMessageEvent(this, message)
    }

}
