package social.alone.server.event.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import social.alone.server.common.controller.BaseController;
import social.alone.server.event.*;
import social.alone.server.oauth2.user.CurrentUser;
import social.alone.server.user.User;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/api/events")
@AllArgsConstructor
public class EventController extends BaseController {

  @Autowired
  private final EventValidator eventValidator;
  @Autowired
  private final EventService eventService;


  @PostMapping
  public ResponseEntity createEvent(
          @CurrentUser User user,
          @RequestBody @Valid EventDto eventDto,
          Errors errors
  ) {

    eventValidator.validate(eventDto, errors);

    if (errors.hasErrors()) {
      return BadRequest(errors);
    }

    Event event = eventService.create(eventDto, user);

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
