package social.alone.server.post.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import social.alone.server.auth.oauth2.user.CurrentUser
import social.alone.server.controller.BaseController
import social.alone.server.post.domain.Post
import social.alone.server.post.dto.PostCreateRequest
import social.alone.server.post.repository.PostRepository
import social.alone.server.post.service.PostCreateService
import social.alone.server.user.domain.User

@Controller
@RequestMapping(value = ["/posts"])
class PostController(
        val postCreateService: PostCreateService,
        val postRepository: PostRepository
) : BaseController() {

    @GetMapping("/{id}")
    fun getPost(
            @PathVariable("id") post: Post
    ): ResponseEntity<*> {
        return ResponseEntity.ok(post)
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    fun createPost(
            @CurrentUser user: User,
            @RequestBody postCreateRequest: PostCreateRequest
    ): ResponseEntity<*> {
        val post = postCreateService.create(user, postCreateRequest)
        return ResponseEntity.ok(post)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    fun deletePost(
            @CurrentUser user: User,
            @PathVariable("id") post: Post
    ): ResponseEntity<*> {
        if (!post.isAuthor(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build<Any>()
        }
        postRepository.delete(post)
        return ResponseEntity.noContent().build<Any>()
    }

}
