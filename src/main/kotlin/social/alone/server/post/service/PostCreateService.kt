package social.alone.server.post.service

import org.springframework.stereotype.Service
import social.alone.server.post.repository.PostRepository
import social.alone.server.image.ImageRepository
import social.alone.server.user.domain.User
import social.alone.server.post.domain.Post
import social.alone.server.post.dto.PostDto

@Service
class PostCreateService(
    private val postRepository: PostRepository,
    private val imageRepository: ImageRepository
){
    fun create(author: User, postDto: PostDto): Post{
        val text = postDto.text
        val image = imageRepository.findById(postDto.image).get()
        return postRepository.save(Post(author, text, image))
    }
}
