package social.alone.server.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import social.alone.server.common.controller.BaseController;
import social.alone.server.user.User;
import social.alone.server.user.UserEnrollService;
import social.alone.server.user.UserResource;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController extends BaseController {

    private final AuthTokenGenerator authTokenGenerator;
    private final UserEnrollService userEnrollService;

    @PostMapping("/login/facebook")
    public ResponseEntity<?> facebookLogin(
            @RequestBody @Valid @NotEmpty String accessToken,
            Errors errors
    ) {

        if (errors.hasErrors()) {
            return BadRequest(errors);
        }

        User user = userEnrollService.byFacebook(accessToken);
        String token = authTokenGenerator.byUser(user);

        var userResource = new UserResource(user);
        userResource.setToken(token);
        return ResponseEntity.ok(userResource);
    }


//    @PostMapping("/login/email")
//    public ResponseEntity<?> authenticateUser(
//            @Valid @RequestBody LoginRequestDto loginRequestDto,
//            Errors errors
//    ) {
//
//        if (errors.hasErrors()) {
//            return BadRequest(errors);
//        }
//
//        Optional<User> byEmail = userRepository.findByEmail(loginRequestDto.getEmail());
//
//        if (byEmail.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        var userResource = new UserResource(byEmail.orElseThrow());
//        var token = authTokenGenerator.byEmailPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword());
//        userResource.setToken(token);
//        return ResponseEntity.ok(userResource);
//    }
//
//    @PostMapping("/signup/email")
//    public ResponseEntity<?> registerUser(
//            @Valid @RequestBody SignUpRequestDto signUpRequestDto,
//            Errors errors
//    ) {
//
//        if (errors.hasErrors()) {
//            return BadRequest(errors);
//        }
//
//        if (userRepository.existsByEmail(signUpRequestDto.getEmail())) {
//            throw new BadRequestException("Email address already in use.");
//        }
//
//        // Creating user's account
//        User user = userEnrollService.enrollByEmailPassword(
//                signUpRequestDto.getEmail(),
//                signUpRequestDto.getPassword(),
//                signUpRequestDto.getName()
//        );
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentContextPath().path("/user/me")
//                .buildAndExpand(user.getId()).toUri();
//
//        var userResource = new UserResource(user);
//        var token = authTokenGenerator.byEmailPassword(
//                signUpRequestDto.getEmail(),
//                signUpRequestDto.getPassword()
//        );
//        userResource.setToken(token);
//
//        return ResponseEntity.created(location)
//                .body(userResource);
//    }

}
