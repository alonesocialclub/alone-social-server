package com.freestudy.api.interest;

import com.freestudy.api.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@Entity
@Table(
        indexes = {
                @Index(name = "idx_value", columnList = "value", unique = true)
        }
)
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Data
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
