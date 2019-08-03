package social.alone.server.event.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import social.alone.server.auth.oauth2.user.CurrentUser
import social.alone.server.controller.BaseController
import social.alone.server.event.domain.Event
import social.alone.server.event.service.EventCreateSvc
import social.alone.server.event.dto.EventValidator
import social.alone.server.event.dto.EventDto
import social.alone.server.event.service.EventDeleteService
import social.alone.server.event.service.EventService
import social.alone.server.user.domain.User

import javax.validation.Valid

@Controller
@RequestMapping(value = ["/api/events"])
class EventMutationController : BaseController() {

    @Autowired
    lateinit var eventValidator: EventValidator

    @Autowired
    lateinit var  eventService: EventService

    @Autowired
    lateinit var  eventDeleteService: EventDeleteService

    @Autowired
    lateinit var  eventCreateSvc: EventCreateSvc

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    fun createEvent(
            @CurrentUser user: User,
            @RequestBody @Valid eventDto: EventDto,
            errors: Errors
    ): ResponseEntity<*> {
        eventValidator.validate(eventDto, errors)

        if (errors.hasErrors()) {
            return BadRequest(errors)
        }

        val event = eventCreateSvc.create(eventDto, user)

        return ResponseEntity.ok(event)
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    fun updateEvent(
            @PathVariable("id") event: Event?,
            @RequestBody @Valid eventDto: EventDto,
            errors: Errors): ResponseEntity<*> {

        if (event == null) {
            return ResponseEntity.notFound().build<Any>()
        }

        if (errors.hasErrors()) {
            return BadRequest(errors)
        }

        eventValidator.validate(eventDto, errors)

        if (errors.hasErrors()) {
            return BadRequest(errors)
        }

        val updatedEvent = eventService.update(event, eventDto)
        return ResponseEntity.ok(updatedEvent)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    fun deleteEvent(
            @CurrentUser user: User,
            @PathVariable("id") event: Event?
    ): ResponseEntity<*> {

        if (event == null) {
            return NotFound()
        }

        if (user != event.owner) {
            return forbidden()
        }

        eventDeleteService.delete(event.id)

        return ResponseEntity.noContent().build<Any>()
    }

}
