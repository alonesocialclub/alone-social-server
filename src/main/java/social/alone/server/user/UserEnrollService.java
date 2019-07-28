package social.alone.server.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import social.alone.server.auth.FacebookUserInfoFetcher;
import social.alone.server.auth.oauth2.user.FacebookOAuth2UserInfo;

import org.springframework.transaction.annotation.Transactional;
import social.alone.server.infrastructure.S3Uploader;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserEnrollService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final FacebookUserInfoFetcher facebookUserInfoFetcher;

    private final S3Uploader s3Uploader;

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

    public User byFacebook(String accessToken){
        FacebookOAuth2UserInfo userInfo = facebookUserInfoFetcher.getUserInfo(accessToken);
//        userInfo.setS3ImageUrl(
//                s3Uploader.upload(userInfo.getId() + "_fb_profile.jpeg", userInfo.getImageUrl())
//        );
        return this.enrollByFacebook(userInfo);
    }

    private User enrollByFacebook(
            FacebookOAuth2UserInfo userInfo) {
        String email = userInfo.getEmail();
        Optional<User> byEmail = userRepository.findByEmailAndProvider(email, AuthProvider.facebook);
        return byEmail.orElseGet(() -> userRepository.save(new User(userInfo, AuthProvider.facebook)));
    }

}
