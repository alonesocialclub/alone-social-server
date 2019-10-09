package social.alone.server.post.dto

import social.alone.server.image.ImageRequest


data class PostCreateRequest(
        val text: String,
        val image: ImageRequest
)
