package social.alone.server.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import social.alone.server.auth.oauth2.user.FacebookOAuth2UserInfo;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserEnrollService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User enrollByEmailPassword(
            String email, String password, String name
    ) {
        Assert.notNull(email, "required");
        Assert.notNull(password, "required");
        Assert.notNull(name, "required");

        User user = User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        return userRepository.save(user);
    }

    public User enrollByFacebook(
            FacebookOAuth2UserInfo userInfo) {
        Optional<User> byEmail = userRepository.findByEmailAndProvider(userInfo.getEmail(), AuthProvider.facebook);
        return byEmail.orElseGet(() -> userRepository.save(new User(userInfo, AuthProvider.facebook)));
    }

}
