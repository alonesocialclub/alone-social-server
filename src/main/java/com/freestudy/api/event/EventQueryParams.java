package com.freestudy.api.event;

import com.freestudy.api.event.type.Coordinate;
import com.freestudy.api.event.type.EventQueryType;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
public class EventQueryParams {
  private Optional<EventQueryType> type;

  @Setter
  private Double longitude;
  @Setter
  private Double latitude;

  public void setType(final EventQueryType type) {
    this.type = Optional.ofNullable(type);
  }

  public Optional<Coordinate> getCoordinate() {
    if (this.latitude != null && this.longitude != null) {
      return Optional.of(new Coordinate(longitude, latitude));
    }
    return Optional.empty();
  }
}
