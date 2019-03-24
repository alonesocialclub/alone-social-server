package social.alone.server.event;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;
import social.alone.server.event.type.EventType;
import social.alone.server.infra.slack.SlackMessagable;
import social.alone.server.infra.slack.SlackMessageEvent;
import social.alone.server.link.Link;
import social.alone.server.location.Location;
import social.alone.server.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString(of = {"name", "startedAt"})
public class Event extends AbstractAggregateRoot<Event> implements SlackMessagable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String name;

  @Column
  private String description;

  @Column
  private LocalDateTime startedAt;

  @Column
  private LocalDateTime endedAt;

  @Column
  private int limitOfEnrollment;

  @Column
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column
  @UpdateTimestamp
  protected LocalDateTime updatedAt;

  @ManyToOne(
          fetch = FetchType.EAGER,
          cascade = { CascadeType.PERSIST, CascadeType.MERGE}, optional = false
  )
  @JoinColumn(name="location_id")
  private Location location;

  @ManyToOne
  private User owner;

  @ManyToMany(cascade = {CascadeType.PERSIST})
  @JoinTable(
          name = "event_event_type",
          joinColumns = @JoinColumn(name = "event_id"),
          inverseJoinColumns = @JoinColumn(name = "event_type_id")
  )
  @Setter
  private Set<EventType> eventTypes;

  @ManyToMany
  @JoinTable(
          name = "event_user",
          joinColumns = @JoinColumn(name = "event_id"),
          inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private Set<User> users = new HashSet<>();

  public Event(EventDto eventDto, User user) {
    this.name = eventDto.getName();
    this.description = eventDto.getDescription();
    this.startedAt = eventDto.getStartedAt();
    this.endedAt = eventDto.getEndedAt();
    this.limitOfEnrollment = eventDto.getLimitOfEnrollment();
    this.owner = user;
    this.activityLogEventCreate();
  }

  void update(EventDto eventDto) {
    this.name = eventDto.getName();
    this.description = eventDto.getDescription();
    this.startedAt = eventDto.getStartedAt();
    this.endedAt = eventDto.getEndedAt();
    this.limitOfEnrollment = eventDto.getLimitOfEnrollment();
  }

  void update(Location location){
    this.location = location;
  }

  void joinEvent(User user) {
    if (this.owner.equals(user)) {
      return;
    }
    this.activityLogJoinEvent(user);
    this.users.add(user);
  }

  void joinCancelEvent(User user) {
    if (this.owner.equals(user)) {
      return;
    }
    this.activityLogJoinEventCancel(user);
    this.users.remove(user);
  }

  public Link createLink() {
    return Link.builder().event(this).build();
  }

  public String getLinkHtml() {
    var url = "https://alone.social/events/" + this.id;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd hh");
    var description = this.startedAt.format(formatter) + "시 " + this.location.getName() + "에서";
    var text = String.format(
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
            location.getImageUrl(),
            url
    );

    return text;
  }

  public void updateLocation(Location location){
    this.location = location;
  }

  private void activityLogEventCreate() {
    if (!owner.isAdmin()) {
      var message = this.getOwner() + "님이 " + this.toString() + "를 생성했습니다.";
      this.registerEvent(buildSlackMessageEvent(message));
    }
  }

  private void activityLogJoinEvent(User user) {
    var message = user.getName() + "님이 " + this.toString() + "를 에 참가 신청을 하셨습니다.";
    this.registerEvent(buildSlackMessageEvent(message));
  }

  private void activityLogJoinEventCancel(User user) {
    var message = user.getName() + "님이 " + this.toString() + "를 에 참가 신청을 취소 하셨습니다.";
    this.registerEvent(buildSlackMessageEvent(message));
  }

  @Override
  public SlackMessageEvent buildSlackMessageEvent(String message) {
    return new SlackMessageEvent(this, message);
  }
}
