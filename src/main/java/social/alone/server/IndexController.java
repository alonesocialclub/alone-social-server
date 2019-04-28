package social.alone.server;


import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import social.alone.server.event.controller.EventMutationController;

@RestController
public class IndexController {

    @GetMapping("/")
    public String getIndex() {
        return "Hello world~";
    }

    @GetMapping("/api")
    public ResourceSupport apiIndex() {
        var index = new ResourceSupport();
        index.add(ControllerLinkBuilder.linkTo(EventMutationController.class).withRel("events"));
        index.add(ControllerLinkBuilder.linkTo(EventMutationController.class).slash(":id").withRel("events/:id"));
        return index;
    }

}
