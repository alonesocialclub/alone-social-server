package social.alone.server.event;


import social.alone.server.event.type.EventTypeDto;
import social.alone.server.location.Location;
import lombok.*;
import social.alone.server.location.LocationDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {

  @NotEmpty
  @NotNull
  private String name;

  @NotEmpty
  private String description;

  @Setter
  @NotNull
  private LocationDto location;

  @NotNull
  private LocalDateTime startedAt;

  @NotNull
  private LocalDateTime endedAt;

  @Min(0)
  private int limitOfEnrollment;

  private Set<EventTypeDto> eventTypes;

  public Location getLocation() {
    return location.buildLocation();
  }
}

