package com.freestudy.api.event;

import lombok.*;

import javax.persistence.*;
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

  @Id
  @GeneratedValue
  private Integer id;
  private String name;
  private String description;

  private LocalDateTime beginEnrollmentDateTime;
  private LocalDateTime closeEnrollmentDateTime;
  private LocalDateTime beginEventDateTime;
  private LocalDateTime endEventDateTime;
  private String location;
  private int limitOfEnrollment;

  private int basePrice;
  private int maxPrice;

  private Date createdAt;
  private Date updatedAt;

  @Enumerated(EnumType.STRING)
  @Builder.Default
  private EventStatus statusStatus = EventStatus.DRAFT;
}
