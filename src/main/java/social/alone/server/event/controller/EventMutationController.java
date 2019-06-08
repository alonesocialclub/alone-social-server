package social.alone.server.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import social.alone.server.common.controller.BaseController;
import social.alone.server.event.Event;
import social.alone.server.event.dto.EventDto;
import social.alone.server.event.service.EventDeleteService;
import social.alone.server.event.service.EventService;
import social.alone.server.event.EventValidator;
import social.alone.server.auth.oauth2.user.CurrentUser;
import social.alone.server.user.User;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/api/events")
@RequiredArgsConstructor
public class EventMutationController extends BaseController {

    private final EventValidator eventValidator;
    private final EventService eventService;
    private final EventDeleteService eventDeleteService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
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
    @PreAuthorize("hasRole('USER')")
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

        Event updatedEvent = eventService.update(event, eventDto);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity deleteEvent(
            @CurrentUser User user,
            @PathVariable("id") Event event
    ) {
        if (event == null) {
            return NotFound();
        }

        if (!user.equals(event.getOwner())) {
            return forbidden();
        }

        eventDeleteService.delete(event.getId());

        return ResponseEntity.noContent().build();
    }

}
