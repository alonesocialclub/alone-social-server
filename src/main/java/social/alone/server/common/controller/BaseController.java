package social.alone.server.common.controller;

import social.alone.server.common.validate.ErrorsResource;
import social.alone.server.user.User;
import social.alone.server.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

public class BaseController {

  @Autowired
  UserRepository userRepository;

  protected ResponseEntity BadRequest(Errors errors) {
    return ResponseEntity.badRequest().body(new ErrorsResource(errors));
  }

  protected User getOrNotFound(User currentUser) {
    return currentUser;

  }
}
