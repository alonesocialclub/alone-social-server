package com.freestudy.api.event;

import lombok.*;

import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Event {

  private Integer id;
  private String name;
  private String description;
  private LocalDateTime time;

}
