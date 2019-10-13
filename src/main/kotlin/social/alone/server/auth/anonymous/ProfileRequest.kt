package social.alone.server.auth.anonymous

import social.alone.server.pickture.PictureRequest
import javax.validation.constraints.NotEmpty


data class ProfileRequest(
        @field:NotEmpty(message = "Name is required")
        val name: String,
        val picture: PictureRequest? = null
)