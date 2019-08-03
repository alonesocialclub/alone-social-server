package social.alone.server.event.controller


import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import social.alone.server.auth.oauth2.user.CurrentUser
import social.alone.server.event.service.EventService
import social.alone.server.user.domain.User

@Controller
@RequestMapping(value = ["/api/events/{id}/users"])
class EventUserController(val eventService: EventService) {

    @PostMapping
    fun joinEvent(
            @PathVariable("id") eventId: Long?,
            @CurrentUser user: User
    ): ResponseEntity<*> {
        val event = eventService!!.joinEvent(eventId, user.id)
        return ResponseEntity.ok(event)
    }

    @DeleteMapping
    fun joinEventCancel(
            @PathVariable("id") eventId: Long?,
            @CurrentUser user: User
    ): ResponseEntity<*> {
        val event = eventService!!.joinEventCancel(eventId, user.id)
        return ResponseEntity.ok(event)
    }
}
