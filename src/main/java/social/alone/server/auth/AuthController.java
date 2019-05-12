package social.alone.server.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import social.alone.server.common.controller.BaseController;
import social.alone.server.common.exception.BadRequestException;
import social.alone.server.auth.oauth2.user.TokenProvider;
import social.alone.server.user.User;
import social.alone.server.user.UserRepository;
import social.alone.server.user.UserResource;
import social.alone.server.user.UserService;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController extends BaseController {

  private final AuthenticationManager authenticationManager;

  private final UserRepository userRepository;

  private final UserService userService;

  private final TokenProvider tokenProvider;

  @PostMapping("/login")
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
    userResource.setToken(authenticate(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
    return ResponseEntity.ok(userResource);
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(
          @Valid @RequestBody SignUpRequestDto signUpRequestDto,
          Errors errors
  ) {

    if (errors.hasErrors()){
      return BadRequest(errors);
    }

    if (userRepository.existsByEmail(signUpRequestDto.getEmail())) {
      throw new BadRequestException("Email address already in use.");
    }

    // Creating user's account
    User user = userService.createLocalAuthUser(signUpRequestDto);

    URI location = ServletUriComponentsBuilder
            .fromCurrentContextPath().path("/user/me")
            .buildAndExpand(user.getId()).toUri();

    var userResource = new UserResource(user);
    userResource.setToken(authenticate(signUpRequestDto.getEmail(), signUpRequestDto.getPassword()));

    return ResponseEntity.created(location)
            .body(userResource);
  }

  private String authenticate(String email, String password) {
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password)
    );

    String token = tokenProvider.createToken(authentication);

    SecurityContextHolder.getContext().setAuthentication(authentication);

    return token;
  }


}
