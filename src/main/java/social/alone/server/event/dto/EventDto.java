package social.alone.server.event.dto;


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
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {

  @NotEmpty
  @NotNull
  public String name;

  @NotEmpty
  public String description;

  @NotNull
  public LocationDto location;

  @NotNull
  public LocalDateTime startedAt;

  @NotNull
  public LocalDateTime endedAt;

  @Min(0)
  public int limitOfEnrollment;

  public Set<EventTypeDto> eventTypes;

  public Location getLocation() {
    return location.buildLocation();
  }
}

