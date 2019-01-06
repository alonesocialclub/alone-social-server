package com.freestudy.api.event;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.freestudy.api.user.User;
import com.freestudy.api.user.UserSerializer;
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

  @Column
  private String name;

  @Column
  private String description;

  @Column
  private LocalDateTime startedAt;

  @Column
  private LocalDateTime endedAt;

  @Column
  private String location;

  @Column
  private int limitOfEnrollment;

  @Column
  private Date createdAt;

  @Column
  private Date updatedAt;

  @ManyToOne
  @JsonSerialize(using = UserSerializer.class)
  private User owner;

  private EventStatus statusStatus; // TODO, startedAt, endedAt 기반으로 getter 만

}
