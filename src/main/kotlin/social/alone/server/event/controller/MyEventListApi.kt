package social.alone.server.event.controller


import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import social.alone.server.auth.oauth2.user.CurrentUser
import social.alone.server.event.service.EventSearchService
import social.alone.server.event.type.EventQueryParams
import social.alone.server.user.domain.User
import javax.validation.Valid

@Controller
@RequestMapping(value = ["/api/events/my"])
class MyEventListApi(private val eventSearchService: EventSearchService) {

    @GetMapping("/upcoming")
    fun upcoming(
            pageable: Pageable,
            @CurrentUser user: User?,
            @Valid eventQueryParams: EventQueryParams
    ): ResponseEntity<*> {
        if (user == null) {
            return ResponseEntity.badRequest().build<Any>()
        }

        val page = this.eventSearchService!!.findAllBy(
                pageable,
                eventQueryParams
        )
        return ResponseEntity.ok(page)
    }

    @GetMapping("/past")
    fun past(
            pageable: Pageable,
            @CurrentUser user: User?,
            @Valid eventQueryParams: EventQueryParams
    ): ResponseEntity<*> {

        if (user == null) {
            return ResponseEntity.badRequest().build<Any>()
        }

        val page = this.eventSearchService!!.findAllBy(
                pageable,
                eventQueryParams
        )
        return ResponseEntity.ok(page)
    }

}
