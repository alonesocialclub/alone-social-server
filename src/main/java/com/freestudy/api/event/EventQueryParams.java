package com.freestudy.api.event;

import com.freestudy.api.event.type.EventQueryType;
import lombok.Getter;

import java.util.Optional;

@Getter
public class EventQueryParams {
  private Optional<EventQueryType> type;

  public void setType(final EventQueryType type) {
    this.type = Optional.ofNullable(type);
  }
}
