package social.alone.server.user.controller

import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import social.alone.server.auth.oauth2.user.CurrentUser
import social.alone.server.controller.BaseController
import social.alone.server.user.domain.User
import social.alone.server.user.domain.UserResource
import social.alone.server.user.dto.UserDto
import social.alone.server.user.service.UserService
import javax.validation.Valid

@Controller
@RequestMapping(value = ["/api/users"])
@RequiredArgsConstructor
class UserController : BaseController() {

    private val userService: UserService? = null

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    fun getUsersMe(@CurrentUser user: User): ResponseEntity<*> {
        return buildResponse(user)
    }

    @PutMapping("/me")
    @PreAuthorize("hasRole('USER')")
    fun postUsersMe(
            @CurrentUser user: User,
            @Valid @RequestBody userDto: UserDto,
            errors: Errors
    ): ResponseEntity<*> {

        if (errors.hasErrors()) {
            return BadRequest(errors)
        }

        val updatedUser = userService!!.update(user, userDto)

        return buildResponse(updatedUser)
    }

    private fun buildResponse(user: User): ResponseEntity<*> {
        val userResource = UserResource(user)
        return ResponseEntity.ok(userResource)
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable("id") user: User): ResponseEntity<*> {
        return buildResponse(user)
    }

}
