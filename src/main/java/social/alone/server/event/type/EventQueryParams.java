package social.alone.server.event.type;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
public class EventQueryParams {

  private EventQueryType type = EventQueryType.ALL;

  @Setter
  private Double longitude;

  @Setter
  private Double latitude;

  private LocalDateTime startedAt;

  public void setType(final EventQueryType type) {
    this.type = type == null ? EventQueryType.ALL : type;
  }

  public Optional<Coordinate> getCoordinate() {
    if (this.latitude != null && this.longitude != null) {
      return Optional.of(new Coordinate(longitude, latitude));
    }
    return Optional.empty();
  }
}
