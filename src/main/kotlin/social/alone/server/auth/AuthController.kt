package social.alone.server.auth

import org.springframework.http.ResponseEntity
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import social.alone.server.auth.email.LoginRequestDto
import social.alone.server.auth.email.SignUpRequestDto
import social.alone.server.controller.BaseController
import social.alone.server.exception.BadRequestException
import social.alone.server.user.domain.UserResource
import social.alone.server.user.repository.UserRepository
import social.alone.server.user.service.UserEnrollService
import javax.validation.Valid

@RestController
@RequestMapping("/api/auth")
class AuthController(private val userRepository: UserRepository, private val userEnrollService: UserEnrollService, private val authTokenGenerator: AuthTokenGenerator) : BaseController() {

    @PostMapping("/login/facebook")
    fun facebookLogin(
            @RequestBody @Valid dto: FacebookLoginDto,
            errors: Errors
    ): ResponseEntity<*> {

        if (errors.hasErrors()) {
            return BadRequest(errors)
        }

        val user = userEnrollService.byFacebook(dto.facebookAccessToken)
        val token = authTokenGenerator.byUser(user)
        val userResource = UserResource(user, token)
        return ResponseEntity.ok(userResource)
    }


    @PostMapping("/login/email")
    fun authenticateUser(
            @Valid @RequestBody loginRequestDto: LoginRequestDto,
            errors: Errors
    ): ResponseEntity<*> {

        if (errors.hasErrors()) {
            return BadRequest(errors)
        }

        val byEmail = userRepository.findByEmail(loginRequestDto.email)

        if (!byEmail.isPresent) {
            return ResponseEntity.notFound().build<Any>()
        }

        val token = authTokenGenerator.byEmailPassword(loginRequestDto.email, loginRequestDto.password)
        val userResource = UserResource(byEmail.get(), token)
        return ResponseEntity.ok(userResource)
    }

    @PostMapping("/signup/email")
    fun registerUser(
            @Valid @RequestBody signUpRequestDto: SignUpRequestDto,
            errors: Errors
    ): ResponseEntity<*> {

        if (errors.hasErrors()) {
            return BadRequest(errors)
        }

        if (userRepository.existsByEmail(signUpRequestDto.email)!!) {
            throw BadRequestException("Email address already in use.")
        }

        // Creating user's account
        val user = userEnrollService.enrollByEmailPassword(
                signUpRequestDto.email,
                signUpRequestDto.password,
                signUpRequestDto.name
        )

        val location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(user.id).toUri()

        val token = authTokenGenerator.byEmailPassword(
                signUpRequestDto.email,
                signUpRequestDto.password
        )
        val userResource = UserResource(user, token)

        return ResponseEntity.created(location)
                .body(userResource)
    }

}
