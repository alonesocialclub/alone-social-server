package social.alone.server.event.controller;

import social.alone.server.common.controller.BaseController;
import social.alone.server.event.*;
import social.alone.server.oauth2.user.CurrentUser;
import social.alone.server.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping(value = "/api/events")
public class EventController extends BaseController {

  private final EventValidator eventValidator;
  private final EventService eventService;
  private final EventSearchService eventSearchService;

  public EventController(
          EventValidator eventValidator,
          EventService eventService, EventSearchService eventSearchService) {
    this.eventValidator = eventValidator;
    this.eventService = eventService;
    this.eventSearchService = eventSearchService;
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
          final Pageable pageable,
          @CurrentUser final User user,
          @Valid final EventQueryParams eventQueryParams
  ) {
    Page<Event> page = this.eventSearchService.findAllBy(
            pageable,
            Optional.ofNullable(user),
            eventQueryParams
    );
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

  @DeleteMapping("/{id}")
  public ResponseEntity deleteEvent(
          @PathVariable("id") Integer eventId) {

    eventService.delete(eventId);
    return ResponseEntity.noContent().build();
  }



}
