package com.freestudy.api.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class EventController {

  @Autowired
  private final EventRepository eventRepository;

  public EventController(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  @PostMapping
  public ResponseEntity createEvent(@RequestBody Event event) {
    Event newEvent = eventRepository.save(event);
    URI createdURL = linkTo(EventController.class).slash(newEvent.getId()).toUri();
    return ResponseEntity.created(createdURL).body(newEvent);
  }
}
