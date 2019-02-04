package com.freestudy.api.event;

import com.freestudy.api.common.controller.BaseController;
import com.freestudy.api.oauth2.user.CurrentUser;
import com.freestudy.api.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/api/events")
public class EventController extends BaseController {

  private final EventValidator eventValidator;
  private final EventService eventService;

  public EventController(
          EventValidator eventValidator,
          EventService eventService) {
    this.eventValidator = eventValidator;
    this.eventService = eventService;
  }

  @PostMapping
  public ResponseEntity createEvent(
          @RequestBody @Valid EventDto eventDto,
          Errors errors,
          @CurrentUser User user
  ) {

    eventValidator.validate(eventDto, errors);

    if (errors.hasErrors()) {
      return BadRequest(errors);
    }

    Event event = eventService.create(eventDto, user);

    return ResponseEntity.ok(event);
  }

  @GetMapping
  public ResponseEntity queryEvents(
          Pageable pageable
  ) {
    Page<Event> page = this.eventService.findAll(pageable);
    return ResponseEntity.ok(page);
  }

  @GetMapping("/{id}")
  public ResponseEntity getEvent(@PathVariable("id") Event event) {

    if (event == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(event);
  }

  @PutMapping("/{id}")
  public ResponseEntity updateEvent(
          @PathVariable("id") Event event,
          @RequestBody @Valid EventDto eventDto,
          Errors errors) {

    if (event == null) {
      return ResponseEntity.notFound().build();
    }

    if (errors.hasErrors()) {
      return BadRequest(errors);
    }

    eventValidator.validate(eventDto, errors);

    if (errors.hasErrors()) {
      return BadRequest(errors);
    }

    var updatedEvent = eventService.update(event, eventDto);
    return ResponseEntity.ok(updatedEvent);
  }


}
