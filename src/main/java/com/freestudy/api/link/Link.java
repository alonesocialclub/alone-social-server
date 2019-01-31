package com.freestudy.api.link;

import com.freestudy.api.event.Event;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter
@EqualsAndHashCode(of = "id")
@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Link {


  @Id
  @GeneratedValue
  private Integer id;

  @ManyToOne
  private Event event;

  @Builder
  public Link(Event event) {
    this.event = event;
  }

}
