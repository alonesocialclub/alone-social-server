package com.freestudy.api.interest;

import com.freestudy.api.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Builder
@Getter
@Setter
@Entity
public class Interest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String value;

  @ManyToMany(mappedBy = "interests")
  @Builder.Default
  private Set<User> users = Set.of();
}
