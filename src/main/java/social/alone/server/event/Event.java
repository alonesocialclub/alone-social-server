package social.alone.server.event;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;
import social.alone.server.event.dto.EventDto;
import social.alone.server.event.type.EventType;
import social.alone.server.common.infrastructure.slack.SlackMessagable;
import social.alone.server.common.infrastructure.slack.SlackMessageEvent;
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
  @GeneratedValue
  public Long id;

  public String name;

  public String description;

  public LocalDateTime startedAt;

  public LocalDateTime endedAt;

  public int limitOfEnrollment;

  @CreationTimestamp
  public LocalDateTime createdAt;

  @UpdateTimestamp
  protected LocalDateTime updatedAt;

  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name="location_id")
  public Location location;

  @ManyToOne
  public User owner;

  @ManyToMany(cascade = {CascadeType.PERSIST})
  @JoinTable(
          name = "event_event_type",
          joinColumns = @JoinColumn(name = "event_id"),
          inverseJoinColumns = @JoinColumn(name = "event_type_id")
  )
  @Setter
  public Set<EventType> eventTypes;

  @ManyToMany
  @JoinTable(
          name = "event_user",
          joinColumns = @JoinColumn(name = "event_id"),
          inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  public Set<User> users = new HashSet<>();

  public Event(EventDto eventDto, User user, Location location) {
    this.updateByEventDto(eventDto);
    this.owner = user;
    this.location = location;
    this.activityLogEventCreate();
  }

  public Event(EventDto eventDto, User user) {
    this.owner = user;
    this.updateByEventDto(eventDto);
    this.activityLogEventCreate();
  }

  public void updateByEventDto(EventDto eventDto){
    this.name = eventDto.getName();
    this.description = eventDto.getDescription();
    this.startedAt = eventDto.getStartedAt();
    this.endedAt = eventDto.getEndedAt();
    this.limitOfEnrollment = eventDto.getLimitOfEnrollment();
  }

  public void joinEvent(User user) {
    if (this.owner.equals(user)) {
      return;
    }
    this.activityLogJoinEvent(user);
    this.users.add(user);
  }

  public void joinCancelEvent(User user) {
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
    String url = "https://alone.social/events/" + this.id;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd hh");
    String description = this.startedAt.format(formatter) + "시 " + this.location.getName() + "에서";
    String text = String.format(
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

  public void activityLogEventCreate() {
    if (!owner.isAdmin()) {
      String message = this.getOwner() + "님이 " + this.toString() + "를 생성했습니다.";
      this.registerEvent(buildSlackMessageEvent(message));
    }
  }

  public void activityLogJoinEvent(User user) {
    String message = user.getName() + "님이 " + this.toString() + "를 에 참가 신청을 하셨습니다.";
    this.registerEvent(buildSlackMessageEvent(message));
  }

  public void activityLogJoinEventCancel(User user) {
    String message = user.getName() + "님이 " + this.toString() + "를 에 참가 신청을 취소 하셨습니다.";
    this.registerEvent(buildSlackMessageEvent(message));
  }

  @Override
  public SlackMessageEvent buildSlackMessageEvent(String message) {
    return new SlackMessageEvent(this, message);
  }
}
