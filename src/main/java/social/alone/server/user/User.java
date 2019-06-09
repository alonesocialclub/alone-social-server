package social.alone.server.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;
import social.alone.server.auth.oauth2.user.OAuth2UserInfo;
import social.alone.server.common.infrastructure.slack.SlackMessagable;
import social.alone.server.common.infrastructure.slack.SlackMessageEvent;
import social.alone.server.event.Event;
import social.alone.server.interest.Interest;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString(of = {"id", "name"})
public class User extends AbstractAggregateRoot<User> implements SlackMessagable {

  @Builder
  public User(String email, String password, String name) {
    this.roles.add(UserRole.USER);
    this.interests = new HashSet<>();

    this.email = email;
    this.password = password;
    this.name = name;
    this.provider = AuthProvider.local;
    this.sendSlackActivityMsg();
  }

  public User(OAuth2UserInfo oAuth2UserInfo, AuthProvider provider) {
    this.roles.add(UserRole.USER);
    this.interests = new HashSet<>();

    this.name = oAuth2UserInfo.getName();
    this.email = oAuth2UserInfo.getEmail();
    this.imageUrl = oAuth2UserInfo.getImageUrl();
    this.provider = provider;
    this.providerId = oAuth2UserInfo.getId();
    this.sendSlackActivityMsg();
  }


  @Id
  @GeneratedValue
  private Long id;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  protected LocalDateTime updatedAt;

  @NotNull
  @Column(nullable = false)
  @Setter
  private String name;

  @NotNull
  @Email
  @Column(nullable = false)
  @Setter
  @JsonIgnore
  private String email;

  @Setter
  private String imageUrl;

  @JsonIgnore
  private String password;

  @NotNull
  @Enumerated(EnumType.STRING)
  private AuthProvider provider;

  @JsonIgnore
  private String providerId;

  @Enumerated(EnumType.STRING)
  @ElementCollection(fetch = FetchType.EAGER)
  private Set<UserRole> roles = new HashSet<>();


  @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
  @JoinTable(
          name = "user_interest",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "interest_id")
  )
  private Set<Interest> interests;

  @ManyToMany(mappedBy = "users")
  @JsonIgnore
  private Set<Event> events;

  void setInterests(HashSet<Interest> interests) {
    this.interests = interests;
  }

  public void update(UserDto userDto) {
    if (userDto.getEmail() != null) {
      this.email = userDto.getEmail();
    }
    if (userDto.getName() != null) {
      this.name = userDto.getName();
    }
  }

  public boolean isAdmin() {
    return this.roles.contains(UserRole.ADMIN);
  }

  private void sendSlackActivityMsg() {
    String message = name + "님이 " + provider + "를 통해 가입하셨습니다.";
    this.registerEvent(buildSlackMessageEvent(message));
  }

  @Override
  public SlackMessageEvent buildSlackMessageEvent(String message) {
    return new SlackMessageEvent(this, message);
  }

}
