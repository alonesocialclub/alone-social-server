package com.freestudy.api.event;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.freestudy.api.link.Link;
import com.freestudy.api.user.User;
import com.freestudy.api.user.UserSerializer;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
public class Event {

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
  @JsonSerialize(using = UserSerializer.class)
  private User owner;

  @Setter(value = AccessLevel.NONE)
  private EventStatus statusStatus;

  @ManyToMany(cascade = {CascadeType.PERSIST})
  @JoinTable(
          name = "event_event_type",
          joinColumns = @JoinColumn(name = "event_id"),
          inverseJoinColumns = @JoinColumn(name = "event_type_id")
  )
  @Builder.Default
  @Setter
  private Set<EventType> eventTypes = new HashSet<>();

  void update(EventDto eventDto) {
    if (!eventDto.getName().isEmpty()) {
      name = eventDto.getName();
    }
  }

  public Link createLink() {
    return Link.builder().event(this).build();
  }
}
