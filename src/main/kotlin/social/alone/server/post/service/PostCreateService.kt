package social.alone.server.post.service

import org.springframework.stereotype.Service
import social.alone.server.post.repository.PostRepository
import social.alone.server.picture.PictureRepository
import social.alone.server.user.domain.User
import social.alone.server.post.domain.Post
import social.alone.server.post.dto.PostCreateRequest
import javax.transaction.Transactional

@Service
@Transactional
class PostCreateService(
        private val postRepository: PostRepository,
        private val pictureRepository: PictureRepository
) {
    fun create(author: User, postCreateRequest: PostCreateRequest): Post {
        val text = postCreateRequest.text
        val image = pictureRepository.findById(postCreateRequest.picture.id).get()
        val post = Post(author, text, image)
        return postRepository.save(post)
    }
}
