package social.alone.server.auth.anonymous

import javax.validation.Valid


data class JoinByProfileRequest(
        @field:Valid
        val profile: ProfileRequest
)

