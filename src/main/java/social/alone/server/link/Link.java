package social.alone.server.link;

import social.alone.server.event.Event;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(of = "id")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Link {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Event event;

  @Builder
  public Link(Event event) {
    this.event = event;
  }

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  protected LocalDateTime updatedAt;

}
