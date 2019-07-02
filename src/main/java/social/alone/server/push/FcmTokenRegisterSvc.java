package social.alone.server.push;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social.alone.server.user.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FcmTokenRegisterSvc {

    private final FcmTokenRepository fcmTokenRepository;

    FcmToken register(String token, Optional<User> user) {
        return fcmTokenRepository.save(new FcmToken(token, user));
    }
}
