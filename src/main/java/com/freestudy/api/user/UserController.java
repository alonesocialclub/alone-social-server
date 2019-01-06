package com.freestudy.api.user;

import com.freestudy.api.common.exception.ResourceNotFoundException;
import com.freestudy.api.oauth2.user.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/users", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class UserController {

  @Autowired
  private UserRepository userRepository;


  @GetMapping("/me")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity getCurrentUser(@CurrentUser User user) {
    userRepository.findById(user.getId())
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", user.getId()));

    var userResource = new UserResource(user);

    return ResponseEntity.ok(userResource);
  }
}
