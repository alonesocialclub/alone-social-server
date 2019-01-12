package com.freestudy.api.link;

import com.freestudy.api.event.Event;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
public class Link {


  @Id
  @GeneratedValue
  private Integer id;

  @ManyToOne
  private Event event;

}
