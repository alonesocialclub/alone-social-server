package social.alone.server.post.dto

import social.alone.server.pickture.PictureRequest


data class PostCreateRequest(
        val text: String,
        val picture: PictureRequest
)
