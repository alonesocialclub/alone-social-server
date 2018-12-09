package com.freestudy.api.event;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import java.time.LocalDateTime;
import java.util.Date;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
public class Event {

  @Id @GeneratedValue
  private Integer id;
  private String name;
  private String description;

  private LocalDateTime beginEnrollmentDateTime;
  private LocalDateTime closeEnrollmentDateTime;
  private LocalDateTime beginEventDatetime;
  private LocalDateTime endEventDatetime;
  private String location;
  private int limitOfEnrollment;

  private int basePrice;
  private int maxPrice;

  private Date createdAt;
  private Date updatedAt;

  @Enumerated(EnumType.STRING)
  private EventStatus statusStatus;
}
