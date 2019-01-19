package com.freestudy.api.common.controller;

import com.freestudy.api.common.exception.ResourceNotFoundException;
import com.freestudy.api.common.validate.ErrorsResource;
import com.freestudy.api.user.User;
import com.freestudy.api.user.UserRepository;
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
    return userRepository.findById(currentUser.getId())
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getId()));
  }
}
