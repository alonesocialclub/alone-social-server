package com.freestudy.api.event;

import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public class Event {

  private Integer id;
  private String name;
  private String description;
  private LocalDateTime time;

}
