package social.alone.server.event.type


import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping(value = ["/api/event-types"])
class EventTypeController(private val eventTypeRepository: EventTypeRepository) {

    @GetMapping
    fun queryEvents(
            pageable: Pageable
    ): ResponseEntity<*> {
        val all = this.eventTypeRepository.findAll(pageable)
        return ResponseEntity.ok(all)
    }

}
