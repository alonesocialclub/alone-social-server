package com.freestudy.api.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.freestudy.api.interest.Interest;
import com.freestudy.api.oauth2.user.OAuth2UserInfo;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.security.Provider;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class User {

  @Builder
  public User(String email, String password, String name) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.roles = Set.of(UserRole.USER);
    this.interests = new HashSet<>();
  }

  public User(OAuth2UserInfo oAuth2UserInfo, AuthProvider provider){
    this.name = oAuth2UserInfo.getName();
    this.email = oAuth2UserInfo.getEmail();
    this.imageUrl = oAuth2UserInfo.getImageUrl();
    this.provider = provider;
    this.providerId = oAuth2UserInfo.getId();
  }

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
  @Setter
  private String email;

  @Setter
  private String imageUrl;

  @JsonIgnore
  private String password;

  @NotNull
  @Enumerated(EnumType.STRING)
  private AuthProvider provider = AuthProvider.local;

  private String providerId;

  @Enumerated(EnumType.STRING)
  @ElementCollection(fetch = FetchType.EAGER)
  private Set<UserRole> roles = Set.of(UserRole.USER);


  @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
  @JoinTable(
          name = "user_interest",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "interest_id")
  )
  private Set<Interest> interests;

  void setInterests(Set<Interest> interests) {
    this.interests = interests;
  }

  public User set(UserDto userDto) {
    if (userDto.getEmail() != null) {
      this.email = userDto.getEmail();
    }
    if (userDto.getName() != null) {
      this.name = userDto.getName();
    }
    return this;
  }
}
