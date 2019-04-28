package social.alone.server.common.validate;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.validation.Errors;
import social.alone.server.IndexController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ErrorsResource extends Resource<Errors> {
  public ErrorsResource(Errors content, Link... links) {
    super(content, links);
    add(linkTo(methodOn(IndexController.class).apiIndex()).withRel("index"));
  }
}
