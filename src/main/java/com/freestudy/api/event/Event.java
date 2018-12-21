package com.freestudy.api.event;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.freestudy.api.account.Account;
import com.freestudy.api.account.AccountSerializer;
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
  private Integer limitOfEnrollment;

  @Column
  private Date createdAt;

  @Column
  private Date updatedAt;

  @ManyToOne
  @JsonSerialize(using = AccountSerializer.class)
  private Account owner;

  private EventStatus statusStatus; // TODO, startedAt, endedAt 기반으로 getter 만

}
