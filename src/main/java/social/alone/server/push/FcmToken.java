package social.alone.server.push;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import social.alone.server.user.User;

import javax.persistence.*;
import java.util.Optional;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
class FcmToken {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String value;

    @ManyToOne
    private User user;

    FcmToken(String value, Optional<User> userMaybe) {
        this.value = value;
        this.user = userMaybe.orElse(null);
    }

}
