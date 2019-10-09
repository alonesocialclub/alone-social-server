package social.alone.server.post.service

import org.springframework.stereotype.Service
import social.alone.server.post.repository.PostRepository
import social.alone.server.image.ImageRepository
import social.alone.server.user.domain.User
import social.alone.server.post.domain.Post
import social.alone.server.post.dto.PostCreateRequest
import javax.transaction.Transactional

@Service
@Transactional
class PostCreateService(
    private val postRepository: PostRepository,
    private val imageRepository: ImageRepository
){
    fun create(author: User, postCreateRequest: PostCreateRequest): Post{
        val text = postCreateRequest.text
        val image = imageRepository.findById(postCreateRequest.image).get()
        return postRepository.save(Post(author, text, image))
    }
}
