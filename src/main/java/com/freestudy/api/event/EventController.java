package com.freestudy.api.event;

import com.freestudy.api.common.controller.BaseController;
import com.freestudy.api.oauth2.user.CurrentUser;
import com.freestudy.api.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Controller
@RequestMapping(value = "/api/events")
public class EventController extends BaseController {

  private final EventRepository eventRepository;

  private final ModelMapper modelMapper;

  private final EventValidator eventValidator;

  public EventController(
          EventRepository eventRepository,
          ModelMapper modelMapper,
          EventValidator eventValidator
  ) {
    this.eventRepository = eventRepository;
    this.modelMapper = modelMapper;
    this.eventValidator = eventValidator;
  }

  @PostMapping
  public ResponseEntity createEvent(
          @RequestBody @Valid EventDto eventDto,
          Errors errors
  ) {
    if (errors.hasErrors()) {
      return BadRequest(errors);
    }

    eventValidator.validate(eventDto, errors);

    if (errors.hasErrors()) {
      return BadRequest(errors);
    }

    Event event = modelMapper.map(eventDto, Event.class);
    Event newEvent = this.eventRepository.save(event);

    ControllerLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(newEvent.getId());
    URI createdUri = selfLinkBuilder.toUri();
    EventResource eventResource = new EventResource(event);
    eventResource.add(linkTo(EventController.class).withRel("query-events"));

    return ResponseEntity.created(createdUri).body(eventResource);
  }

  @GetMapping
  public ResponseEntity queryEvents(
          Pageable pageable,
          PagedResourcesAssembler<Event> assembler,
          @CurrentUser User user
  ) {
    Page<Event> page = this.eventRepository.findAll(pageable);
    var pagedResources = assembler.toResource(page, e -> new EventResource(e));
    pagedResources.add(new Link("/docs/index.html#resources-events-list").withRel("profile"));
    if (user != null) {
      pagedResources.add(linkTo(EventController.class).withRel("create-event"));
    }
    return ResponseEntity.ok(pagedResources);

  }

  @GetMapping("/{id}")
  public ResponseEntity getEvent(@PathVariable Integer id) {
    Optional<Event> optionalEvent = this.eventRepository.findById(id);
    if (optionalEvent.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    Event event = optionalEvent.get();
    var resource = new EventResource(event);
    resource.add(new Link("/docs/index.html#resources-event-get").withRel("profile"));
    return ResponseEntity.ok(resource);
  }

  @PutMapping("/{id}")
  public ResponseEntity updateEvent(
          @PathVariable Integer id,
          @RequestBody @Valid EventDto eventDto,
          Errors errors) {
    Optional<Event> optionalEvent = this.eventRepository.findById(id);

    if (optionalEvent.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    if (errors.hasErrors()) {
      return BadRequest(errors);
    }

    eventValidator.validate(eventDto, errors);

    if (errors.hasErrors()) {
      return BadRequest(errors);
    }

    Event event = optionalEvent.get();
    this.modelMapper.map(eventDto, event);
    Event savedEvent = this.eventRepository.save(event);
    var resource = new EventResource(savedEvent);
    resource.add(new Link("/docs/index.html#resources-events-update").withRel("profile"));
    return ResponseEntity.ok(resource);
  }


}
