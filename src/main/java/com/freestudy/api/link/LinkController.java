package com.freestudy.api.link;


import com.freestudy.api.config.AppProperties;
import com.freestudy.api.event.Event;
import com.freestudy.api.event.EventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping(value = "/api/events/{eventId}/links")
public class LinkController {


  @Autowired
  private EventRepository eventRepository;

  @Autowired
  private LinkRepository linkRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private AppProperties appProperties;

  @PostMapping
  public ResponseEntity getLink(
          @PathVariable("eventId") Integer eventId
  ) {
    Optional<Event> optionalEvent = eventRepository.findById(eventId);

    if (optionalEvent.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    var event = optionalEvent.get();
    Link link = Link.builder().event(event).build();
    linkRepository.save(link);

    // TODO view logic
    LinkResponseDTO response = modelMapper.map(link, LinkResponseDTO.class);
    response.setUrl(appProperties.getLink().getHost() + "/link/" + link.getId());

    return ResponseEntity.ok(response);
  }


}
