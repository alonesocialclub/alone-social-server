package com.freestudy.api.user;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class UserResource extends Resource<User> {

  public UserResource(User user, Link... links) {
    super(user, links);
    add(linkTo(UserController.class).slash(user.getId()).withSelfRel());
  }
}
