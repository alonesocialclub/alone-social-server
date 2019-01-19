package com.freestudy.api.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.freestudy.api.interest.Interest;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        }
)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(nullable = false)
  @Setter
  private String name;

  @NotNull
  @Email
  @Column(nullable = false)
  private String email;

  @Setter
  private String imageUrl;

  @JsonIgnore
  private String password;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private AuthProvider provider = AuthProvider.local;

  private String providerId;

  @Enumerated(EnumType.STRING)
  @ElementCollection(fetch = FetchType.EAGER)
  @Builder.Default
  private Set<UserRole> roles = Set.of(UserRole.USER);


  @ManyToMany(
          cascade = {CascadeType.ALL}
  )
  @JoinTable(
          name = "user_interest",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "interest_id")
  )
  @Builder.Default
  private Set<Interest> interests = new HashSet<>();

}
