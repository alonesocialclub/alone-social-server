package social.alone.server.post.dto

import social.alone.server.picture.PictureRequest


data class PostCreateRequest(
        val text: String,
        val picture: PictureRequest
)
