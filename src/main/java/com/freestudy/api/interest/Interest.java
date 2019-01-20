package com.freestudy.api.interest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.freestudy.api.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(
        indexes = {
                @Index(name = "idx_value", columnList = "value", unique = true)
        }
)
@EqualsAndHashCode(of = "id")
public class Interest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String value;

  @ManyToMany(mappedBy = "interests")
  @Builder.Default
  @JsonIgnore
  private Set<User> users = new HashSet<>();
}
