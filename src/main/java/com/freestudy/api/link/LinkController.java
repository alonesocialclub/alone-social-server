package com.freestudy.api.link;


import com.freestudy.api.config.AppProperties;
import com.freestudy.api.event.Event;
import com.freestudy.api.event.EventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/events/{eventId}/links")
public class LinkController {

  private LinkService linkService;

  private ModelMapper modelMapper;

  private AppProperties appProperties;

  @Autowired
  public LinkController(
          LinkService linkService,
          ModelMapper modelMapper,
          AppProperties appProperties) {
    this.linkService = linkService;
    this.modelMapper = modelMapper;
    this.appProperties = appProperties;
  }

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
    response.setUrl(appProperties.getLink().getHost() + "/events/" + event.getId() + "/links/" + link.getId());

    return ResponseEntity.ok(response);
  }

  @GetMapping(value = "/{linkId}", produces = MediaType.TEXT_HTML_VALUE)
  public ResponseEntity getLink(
          @PathVariable("linkId") Link link
  ) {

    if (link == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok("<html><head>" +
            "<title>" + link.getEvent().getName() + "<title/>" +
            "<script>window.location.replace('" + "http://stackoverflow.com" + "');</script>" +
            "</head></html>"
    );
  }

}
