package com.freestudy.api.auth;

import com.freestudy.api.common.exception.BadRequestException;
import com.freestudy.api.user.User;
import com.freestudy.api.user.UserRepository;
import com.freestudy.api.oauth2.user.TokenProvider;
import com.freestudy.api.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private TokenProvider tokenProvider;

  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequestDto) {
    String token = authenticate(loginRequestDto.getEmail(), loginRequestDto.getPassword());
    return ResponseEntity.ok(token);
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
    if (userRepository.existsByEmail(signUpRequestDto.getEmail())) {
      throw new BadRequestException("Email address already in use.");
    }

    // Creating user's account
    User user = userService.createLocalAuthUser(
            signUpRequestDto.getName(),
            signUpRequestDto.getEmail(),
            signUpRequestDto.getPassword()
    );


    URI location = ServletUriComponentsBuilder
            .fromCurrentContextPath().path("/user/me")
            .buildAndExpand(user.getId()).toUri();

    return ResponseEntity.created(location)
            .body("User registered successfully@");
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
