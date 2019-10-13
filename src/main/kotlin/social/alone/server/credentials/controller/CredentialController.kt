package social.alone.server.credentials.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController
import social.alone.server.auth.oauth2.user.CurrentUser
import social.alone.server.credentials.dto.AppleLoginDto
import social.alone.server.credentials.service.CredentialCreateService
import social.alone.server.user.domain.User


@RestController
class CredentialController(private val credentialCreateService: CredentialCreateService) {
    @PutMapping("/me/credentials/apple-id")
    fun putMeAppleIDCredential(
            @CurrentUser user: User,
            @RequestBody appleLoginDto: AppleLoginDto
    ): ResponseEntity<*> {
        val credential = credentialCreateService.create(user, appleLoginDto)
        return ResponseEntity.status(HttpStatus.OK).build<Any>()
    }
}