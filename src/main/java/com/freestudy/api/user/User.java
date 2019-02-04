package com.freestudy.api.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.freestudy.api.interest.Interest;
import com.freestudy.api.oauth2.user.OAuth2UserInfo;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
@EqualsAndHashCode(of = "id", callSuper = true)
public class User extends AbstractAggregateRoot<User> {

  @Builder
  public User(String email, String password, String name) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.roles = Set.of(UserRole.USER);
    this.interests = new ArrayList<>();
    this.provider = AuthProvider.local;
    this.registerEvent(new UserAfterSaveEvent(this));
  }

  public User(OAuth2UserInfo oAuth2UserInfo, AuthProvider provider) {
    this.name = oAuth2UserInfo.getName();
    this.email = oAuth2UserInfo.getEmail();
    this.imageUrl = oAuth2UserInfo.getImageUrl();
    this.roles = Set.of(UserRole.USER);
    this.provider = provider;
    this.providerId = oAuth2UserInfo.getId();
    this.registerEvent(new UserAfterSaveEvent(this));
  }


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column
  @UpdateTimestamp
  protected LocalDateTime updatedAt;

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
  private AuthProvider provider;

  @JsonIgnore
  private String providerId;

  @Enumerated(EnumType.STRING)
  @ElementCollection(fetch = FetchType.EAGER)
  private Set<UserRole> roles;


  @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
  @JoinTable(
          name = "user_interest",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "interest_id")
  )
  private List<Interest> interests;

  void setInterests(List<Interest> interests) {
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
