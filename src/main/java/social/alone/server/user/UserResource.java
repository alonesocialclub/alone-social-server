package social.alone.server.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import social.alone.server.user.controller.UserController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class UserResource extends Resource<User> {

  @Setter
  @Getter
  private String token = null;

  public UserResource(User user, Link... links) {
    super(user, links);
    add(linkTo(UserController.class).slash(user.getId()).withSelfRel());
  }
}
