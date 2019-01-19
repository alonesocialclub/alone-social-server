package com.freestudy.api.user;

import com.freestudy.api.common.controller.BaseController;
import com.freestudy.api.common.exception.ResourceNotFoundException;
import com.freestudy.api.event.EventDto;
import com.freestudy.api.interest.InterestDto;
import com.freestudy.api.interest.InterestService;
import com.freestudy.api.oauth2.user.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/api/users")
public class UserController extends BaseController {


  @Autowired
  private InterestService interestService;

  @GetMapping("/me")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity getUsersMe(@CurrentUser User currentUser) {
    User user = getOrNotFound(currentUser);

    return buildResponse(user);
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
    User user = getOrNotFound(currentUser);

    user.set(userDto);
    if (userDto.getInterests() != null) {
      var interests = interestService.saveAll(userDto.getInterests().stream().map(InterestDto::getValue).collect(Collectors.toList()));
      user.setInterests(interests);
    }

    return buildResponse(user);
  }


  private ResponseEntity buildResponse(User user) {
    var userResource = new UserResource(user);
    return ResponseEntity.ok(userResource);
  }

}
