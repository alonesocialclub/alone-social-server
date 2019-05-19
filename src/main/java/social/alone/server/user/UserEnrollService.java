package social.alone.server.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

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
}
