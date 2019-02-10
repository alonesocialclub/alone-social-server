package com.freestudy.api.user;

import com.freestudy.api.common.controller.BaseController;
import com.freestudy.api.interest.InterestService;
import com.freestudy.api.oauth2.user.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/api/users")
public class UserController extends BaseController {


  @Autowired
  private UserService userService;

  @GetMapping("/me")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity getUsersMe(@CurrentUser User currentUser) {
    return buildResponse(currentUser);
  }

  @PutMapping("/me")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity postUsersMe(
          @CurrentUser User currentUser,
          @Valid @RequestBody UserDto userDto,
          Errors errors
  ) {

    if (errors.hasErrors()) {
      return BadRequest(errors);
    }
    User user = currentUser;

    userService.save(user, userDto);

    return buildResponse(user);
  }


  private ResponseEntity buildResponse(User user) {
    var userResource = new UserResource(user);
    return ResponseEntity.ok(userResource);
  }

}
