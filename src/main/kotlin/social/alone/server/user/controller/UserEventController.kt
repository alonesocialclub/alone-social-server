package social.alone.server.user.controller

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import social.alone.server.event.service.EventSearchService
import social.alone.server.event.type.EventQueryParams
import social.alone.server.user.domain.User
import javax.validation.Valid

@Controller
@RequestMapping(value = ["/api/users/{userId}/events"])
class UserEventController(val eventSearchService: EventSearchService) {

    @GetMapping
    fun getEventsWithUser(
            @PageableDefault(sort = ["startedAt"], direction = Sort.Direction.ASC) pageable: Pageable,
            @PathVariable("userId") user: User,
            @Valid eventQueryParams: EventQueryParams
    ): ResponseEntity<*> {
        val page = eventSearchService.findAllBy(pageable, eventQueryParams)
        return ResponseEntity.ok(page)
    }

}
