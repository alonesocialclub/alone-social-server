package social.alone.server.auth.anonymous

import social.alone.server.pickture.PictureRequest


data class ProfileRequest(
        val name: String,
        val picture: PictureRequest? = null
)