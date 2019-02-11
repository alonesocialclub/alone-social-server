package com.freestudy.api.event;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.freestudy.api.event.location.Location;
import com.freestudy.api.event.location.LocationDeserializer;
import com.freestudy.api.event.location.LocationSerializer;
import com.freestudy.api.event.type.EventType;
import com.freestudy.api.infra.slack.SlackMessagable;
import com.freestudy.api.infra.slack.SlackMessageEvent;
import com.freestudy.api.link.Link;
import com.freestudy.api.user.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@ToString(of = {"name", "startedAt"})
public class Event extends AbstractAggregateRoot<Event> implements SlackMessagable {

  @Id
  @GeneratedValue
  private Integer id;

  @Column
  private String name;

  @Column
  private String description;

  @Column
  private LocalDateTime startedAt;

  @Column
  private LocalDateTime endedAt;

  @Embedded
  @JsonSerialize(using = LocationSerializer.class)
  @JsonDeserialize(using = LocationDeserializer.class)
  private Location location;

  @Column
  private int limitOfEnrollment;

  @Column
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column
  @UpdateTimestamp
  protected LocalDateTime updatedAt;

  @ManyToOne
  private User owner;

  @Setter(value = AccessLevel.NONE)
  private EventStatus statusStatus;

  @ManyToMany(cascade = {CascadeType.PERSIST})
  @JoinTable(
          name = "event_event_type",
          joinColumns = @JoinColumn(name = "event_id"),
          inverseJoinColumns = @JoinColumn(name = "event_type_id")
  )
  private Set<EventType> eventTypes;

  @ManyToMany
  @JoinTable(
          name = "event_user",
          joinColumns = @JoinColumn(name = "event_id"),
          inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private Set<User> users;

  public Event(EventDto eventDto, User user) {
    this.name = eventDto.getName();
    this.description = eventDto.getDescription();
    this.location = eventDto.getLocation();
    this.startedAt = eventDto.getStartedAt();
    this.endedAt = eventDto.getEndedAt();
    this.limitOfEnrollment = eventDto.getLimitOfEnrollment();
    this.owner = user;
    this.sendSlackActivityMsg();
  }

  void update(EventDto eventDto) {
    if (!eventDto.getName().isEmpty()) {
      name = eventDto.getName();
    }
  }

  public void joinEvent(User user) {
    if (this.owner.equals(user)) {
      return;
    }
    this.users.add(user);
  }

  public Link createLink() {
    return Link.builder().event(this).build();
  }


  private void sendSlackActivityMsg() {
    if (!owner.isAdmin()) {
      this.registerEvent(this.buildSlackMessageEvent());
    }
  }

  @Override
  public SlackMessageEvent buildSlackMessageEvent() {
    return new SlackMessageEvent(this, this.getOwner() + "님이 " + this.toString() + "를 생성했습니다.");
  }
}
