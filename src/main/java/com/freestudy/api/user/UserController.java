package com.freestudy.api.user;

import com.freestudy.api.common.exception.ResourceNotFoundException;
import com.freestudy.api.oauth2.user.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

  @Autowired
  private UserRepository userRepository;


  @GetMapping("/me")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity getUsersMe(@CurrentUser User currentUser) {
    User user = getOrNotFound(currentUser);

    return buildResponse(user);
  }

  @PostMapping("/me")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity postUsersMe(@CurrentUser User currentUser) {
    User user = getOrNotFound(currentUser);

    return buildResponse(user);
  }


  private User getOrNotFound(User currentUser) {
    return userRepository.findById(currentUser.getId())
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getId()));
  }

  private ResponseEntity buildResponse(User user) {
    var userResource = new UserResource(user);
    return ResponseEntity.ok(userResource);
  }

}
