package social.alone.server.link;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import social.alone.server.common.config.AppProperties;
import social.alone.server.event.Event;

@Controller
@RequestMapping(value = "/api/events/{eventId}/links")
@RequiredArgsConstructor
public class LinkController {
  //TODO 뭘 하고싶니.?

  private final LinkService linkService;

  private final ModelMapper modelMapper;

  private final AppProperties appProperties;

  @PostMapping
  public ResponseEntity createLink(
          @PathVariable("eventId") Event event
  ) {
    if (event == null) {
      return ResponseEntity.notFound().build();
    }

    Link link = linkService.createLink(event);

    // TODO view logic
    LinkResponseDTO response = modelMapper.map(link, LinkResponseDTO.class);
    response.setUrl(appProperties.getLink().getHost() + "/api/events/" + event.getId() + "/links");

    return ResponseEntity.ok(response);
  }

  @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
  public ResponseEntity getLink(
          @PathVariable("eventId") Event event
  ) {

    if (event == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(event.getLinkHtml());
  }

}
