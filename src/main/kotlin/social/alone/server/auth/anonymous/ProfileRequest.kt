package social.alone.server.auth.anonymous

import social.alone.server.picture.PictureRequest
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size


data class ProfileRequest(
        @field:NotEmpty(message = "Name is required")
        @field:Size(max = 16, message = "Name should be 1~16 characters")
        val name: String,
        val picture: PictureRequest? = null
)