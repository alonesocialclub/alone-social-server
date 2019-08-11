package social.alone.server.event.controller

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.data.web.SortDefault
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import social.alone.server.auth.oauth2.user.CurrentUser
import social.alone.server.controller.BaseController
import social.alone.server.event.domain.Event
import social.alone.server.event.service.EventSearchService
import social.alone.server.event.type.EventQueryParams
import social.alone.server.user.domain.User
import javax.validation.Valid

@Controller
@RequestMapping(value = ["/api/events"])
class EventSearchController(val eventSearchService: EventSearchService) : BaseController() {

    @GetMapping
    fun queryEvents(
            @PageableDefault(sort = ["startedAt"], direction = Sort.Direction.DESC) pageable: Pageable,
            @CurrentUser user: User?,
            @Valid eventQueryParams: EventQueryParams
    ): ResponseEntity<*> {

        val page = this.eventSearchService.findAllBy(
                pageable, eventQueryParams)

        return ResponseEntity.ok(page)
    }

    @GetMapping("/{id}")
    fun getEvent(
            @PathVariable("id") event: Event?
    ): ResponseEntity<*> {
        return if (event == null) {
            ResponseEntity.notFound().build<Any>()
        } else ResponseEntity.ok(event)

    }
}
