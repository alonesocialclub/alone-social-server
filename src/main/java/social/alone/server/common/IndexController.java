package social.alone.server.common;


import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/")
    public String getIndex() {
        return "Hello world~~~~~";
    }

    @GetMapping("/api")
    public ResourceSupport apiIndex() {
        ResourceSupport index = new ResourceSupport();
        return index;
    }

}
