package com.freestudy.api.event.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.freestudy.api.event.Event;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(
        indexes = {
                @Index(name = "idx_value", columnList = "value", unique = true)
        }
)
@EqualsAndHashCode(of = "id")
public class EventType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String value;

  @ManyToMany(mappedBy = "eventTypes")
  @Builder.Default
  @JsonIgnore
  private Set<Event> events = new HashSet<>();

  public static EventType of(String value) {
    return EventType.builder().value(value).build();
  }

  public EventTypeDto toDto() {
    return new EventTypeDto(this.id, this.value);
  }
}