package com.freestudy.api.common.controller;

import com.freestudy.api.common.validate.ErrorsResource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

public class BaseController {


  protected ResponseEntity BadRequest(Errors errors) {
    return ResponseEntity.badRequest().body(new ErrorsResource(errors));
  }
}
