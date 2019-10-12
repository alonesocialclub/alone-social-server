package social.alone.server.auth.anonymous

import social.alone.server.image.ImageRequest


data class ProfileRequest(
        val name: String,
        val image: ImageRequest? = null
)