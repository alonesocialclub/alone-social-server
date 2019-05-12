package social.alone.server.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import social.alone.server.common.validate.ErrorsResource;

public class BaseController {

  protected ResponseEntity BadRequest(Errors errors) {
    return ResponseEntity.badRequest().body(new ErrorsResource(errors));
  }

  protected ResponseEntity NotFound() {
    return ResponseEntity.notFound().build();
  }

  protected ResponseEntity forbidden(){
    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }
}
