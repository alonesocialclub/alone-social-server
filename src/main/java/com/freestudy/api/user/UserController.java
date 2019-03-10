package com.freestudy.api.user;

import com.freestudy.api.common.controller.BaseController;
import com.freestudy.api.oauth2.user.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/api/users")
public class UserController extends BaseController {

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/me")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity getUsersMe(@CurrentUser User user) {
    return buildResponse(user);
  }

  @PutMapping("/me")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity postUsersMe(
          @CurrentUser User user,
          @Valid @RequestBody UserDto userDto,
          Errors errors
  ) {

    if (errors.hasErrors()) {
      return BadRequest(errors);
    }

    User updatedUser = userService.update(getOrNotFound(user), userDto);

    return buildResponse(updatedUser);
  }

  private ResponseEntity buildResponse(User user) {
    var userResource = new UserResource(user);
    return ResponseEntity.ok(userResource);
  }

  @GetMapping("/{id}")
  public ResponseEntity getUser(@PathVariable("id") User user) {
    return buildResponse(user);
  }

}
