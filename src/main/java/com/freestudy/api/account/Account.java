package com.freestudy.api.account;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
public class Account {

  @Id
  @GeneratedValue
  private Integer id;

  @Column
  private String email;

  @Column
  private String password;

  @Enumerated(EnumType.STRING)
  @ElementCollection(fetch = FetchType.EAGER)
  private Set<AccountRole> roles;

}
