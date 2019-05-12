package social.alone.server.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import social.alone.server.common.controller.BaseController;
import social.alone.server.auth.oauth2.user.CurrentUser;
import social.alone.server.user.User;
import social.alone.server.user.UserDto;
import social.alone.server.user.UserResource;
import social.alone.server.user.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
public class UserController extends BaseController {

  private final UserService userService;

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

    User updatedUser = userService.update(user, userDto);

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
