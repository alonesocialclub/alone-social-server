package social.alone.server.auth.anonymous

import org.springframework.http.ResponseEntity
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import social.alone.server.auth.AuthTokenGenerator
import social.alone.server.user.domain.UserTokenView
import social.alone.server.user.service.UserEnrollService
import social.alone.server.validate.ErrorsResource
import javax.validation.Valid

@RestController
class AnonymousJoinController(

        private val userEnrollService: UserEnrollService,
        private val authTokenGenerator: AuthTokenGenerator
) {

    @PostMapping("/anonymous/join")
    fun anonymousJoin(
            @Valid @RequestBody request: JoinByProfileRequest,
            errors: Errors
    ): ResponseEntity<*> {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorsResource(errors))
        }
        val user = userEnrollService.byProfile(request)
        val token = authTokenGenerator.byUser(user)
        val userResource = UserTokenView(user, token)
        return ResponseEntity.ok(userResource)
    }
}