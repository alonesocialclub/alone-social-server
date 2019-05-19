package social.alone.server.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import social.alone.server.auth.email.LoginRequestDto;
import social.alone.server.auth.email.SignUpRequestDto;
import social.alone.server.auth.oauth2.user.FacebookOAuth2UserInfo;
import social.alone.server.common.controller.BaseController;
import social.alone.server.common.exception.BadRequestException;
import social.alone.server.user.User;
import social.alone.server.user.UserEnrollService;
import social.alone.server.user.UserRepository;
import social.alone.server.user.UserResource;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController extends BaseController {

    private final AuthTokenGenerator authTokenGenerator;

    private final UserRepository userRepository;
    private final UserEnrollService userEnrollService;
    private final FacebookUserInfoFetcher facebookUserInfoFetcher;

    @PostMapping("/login/facebook")
    public ResponseEntity<?> facebookLogin(
            @RequestBody @Valid @NotEmpty String accessToken,
            Errors errors
    ) {

        if (errors.hasErrors()) {
            return BadRequest(errors);
        }

        FacebookOAuth2UserInfo userInfo = facebookUserInfoFetcher.getUserInfo(accessToken);
        User user = userEnrollService.enrollByFacebook(userInfo);

        var userResource = new UserResource(user);
        userResource.setToken(authTokenGenerator.byUser(user));
        return ResponseEntity.ok(userResource);
    }


    @PostMapping("/login/email")
    public ResponseEntity<?> authenticateUser(
            @Valid @RequestBody LoginRequestDto loginRequestDto,
            Errors errors
    ) {

        if (errors.hasErrors()) {
            return BadRequest(errors);
        }

        Optional<User> byEmail = userRepository.findByEmail(loginRequestDto.getEmail());

        if (byEmail.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var userResource = new UserResource(byEmail.orElseThrow());
        var token = authTokenGenerator.byEmailPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        userResource.setToken(token);
        return ResponseEntity.ok(userResource);
    }

    @PostMapping("/signup/email")
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody SignUpRequestDto signUpRequestDto,
            Errors errors
    ) {

        if (errors.hasErrors()) {
            return BadRequest(errors);
        }

        if (userRepository.existsByEmail(signUpRequestDto.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        // Creating user's account
        User user = userEnrollService.enrollByEmailPassword(
                signUpRequestDto.getEmail(),
                signUpRequestDto.getPassword(),
                signUpRequestDto.getName()
        );

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(user.getId()).toUri();

        var userResource = new UserResource(user);
        var token = authTokenGenerator.byEmailPassword(
                signUpRequestDto.getEmail(),
                signUpRequestDto.getPassword()
        );
        userResource.setToken(token);

        return ResponseEntity.created(location)
                .body(userResource);
    }

}
