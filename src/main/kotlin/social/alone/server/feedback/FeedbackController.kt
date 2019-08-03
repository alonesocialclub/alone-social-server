package social.alone.server.feedback


import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import social.alone.server.auth.oauth2.user.CurrentUser
import social.alone.server.slack.SlackNotifier
import social.alone.server.user.domain.User
import javax.validation.Valid

@Controller
@RequestMapping(value = ["/api/feedbacks"])
class FeedbackController(val slackNotifier: SlackNotifier) {

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    fun postFeedback(
            @CurrentUser user: User,
            @Valid @RequestBody req: FeedbackRequest

    ): ResponseEntity<*> {
        slackNotifier.send("user(" + user.id + ") feedback : " + req.text)
        return ResponseEntity.noContent().build<Any>()
    }

}
