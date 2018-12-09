package com.freestudy.api.event;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;


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
  private LocalDateTime beginEnrollmentDateTime;
  private LocalDateTime closeEnrollmentDateTime;
  private LocalDateTime beginEventDatetime;
  private LocalDateTime endEventDatetime;
  private String location;
  private int price;
  private int limitOfEnrollment;

  private Date createdAt;
  private Date updatedAt;
}
