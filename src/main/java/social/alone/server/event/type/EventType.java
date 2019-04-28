package social.alone.server.event.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import social.alone.server.event.Event;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        indexes = {
                @Index(name = "idx_value", columnList = "value", unique = true)
        }
)
@EqualsAndHashCode(of = "id")
public class EventType {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, unique = true)
  private String value;

  @ManyToMany(mappedBy = "eventTypes")
  @JsonIgnore
  private Set<Event> events = new HashSet<>();

  public EventType(String value) {
    this.value = value;
  }

  public static EventType of(String value) {
    return new EventType(value);
  }

  public EventTypeDto toDto() {
    return new EventTypeDto(this.id, this.value);
  }
}