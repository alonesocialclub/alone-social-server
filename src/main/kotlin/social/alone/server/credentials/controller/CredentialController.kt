package social.alone.server.credentials.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController
import social.alone.server.auth.AuthTokenGenerator
import social.alone.server.auth.oauth2.user.CurrentUser
import social.alone.server.credentials.dto.AppleLoginDto
import social.alone.server.credentials.service.CredentialCreateService
import social.alone.server.credentials.service.CredentialLoginService
import social.alone.server.user.domain.User
import social.alone.server.user.domain.UserTokenView


@RestController
class CredentialController(
        private val credentialCreateService: CredentialCreateService,
        private val credentialLoginService: CredentialLoginService,
        private val authTokenGenerator: AuthTokenGenerator
) {

    @PostMapping("/login/apple")
    fun loginWithAppleIDCredential(
            @RequestBody appleLoginDto: AppleLoginDto
    ): ResponseEntity<*> {
        val credential = credentialLoginService.getUser(appleLoginDto)
        if (credential == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build<Any>()
        }
        val user = credential.user
        val token = authTokenGenerator.byUser(user)
        val userResource = UserTokenView(user, token)
        return ResponseEntity.ok(userResource)
    }

    @PutMapping("/me/credentials/apple")
    fun putMeAppleIDCredential(
            @CurrentUser user: User,
            @RequestBody appleLoginDto: AppleLoginDto
    ): ResponseEntity<*> {
        val credential = credentialCreateService.create(user, appleLoginDto)
        return ResponseEntity.status(HttpStatus.OK).build<Any>()
    }
}