package com.freestudy.api.interest;

import com.freestudy.api.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
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

  @ManyToMany(mappedBy = "interests", cascade = {CascadeType.ALL})
  @Builder.Default
  private Set<User> users = new HashSet<>();
}
