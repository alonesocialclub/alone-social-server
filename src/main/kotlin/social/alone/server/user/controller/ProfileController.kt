package social.alone.server.user.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import social.alone.server.auth.oauth2.user.CurrentUser
import social.alone.server.controller.BaseController
import social.alone.server.user.domain.User
import social.alone.server.user.domain.UserTokenView
import social.alone.server.user.dto.ProfileDto
import social.alone.server.user.service.ProfileService
import javax.validation.Valid

@Controller
@RequestMapping(value = ["/api/users"])
class ProfileController (val userProfileService: ProfileService): BaseController() {
    @GetMapping("/{id}/profile")
    fun getUserProfile(
            @PathVariable("id") user: User
    ): ResponseEntity<*> {
        return ResponseEntity.ok(user)
    }

    @PatchMapping("/me/profile")
    fun patchMeProfile(
            @CurrentUser user: User,
            @RequestBody profileDto: ProfileDto
    ): ResponseEntity<*> {
        return ResponseEntity.ok(userProfileService.patch(user, profileDto))
    }

    private fun buildResponse(user: User): ResponseEntity<*> {
        val userResource = UserTokenView(user)
        return ResponseEntity.ok(userResource)
    }
}
