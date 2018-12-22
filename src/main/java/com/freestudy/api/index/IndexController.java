package com.freestudy.api.index;


import com.freestudy.api.event.EventController;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

  @GetMapping("/")
  public String getIndex() {
    return "Hello world!!!";
  }

  @GetMapping("/api")
  public ResourceSupport apiIndex() {
    var index = new ResourceSupport();
    index.add(ControllerLinkBuilder.linkTo(EventController.class).withRel("events"));
    index.add(ControllerLinkBuilder.linkTo(EventController.class).slash(":id").withRel("events/:id"));
    return index;
  }

}
