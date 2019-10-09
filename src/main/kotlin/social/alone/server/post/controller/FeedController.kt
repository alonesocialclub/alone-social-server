package social.alone.server.post.controller

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import social.alone.server.post.domain.Post
import social.alone.server.post.repository.PostRepository


@RestController
class FeedController(val postRepository: PostRepository){

    @GetMapping("/feed")
    fun getPosts(
            @PageableDefault(sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable
    ): Page<Post> {
        return postRepository.findAll(pageable)
    }
}