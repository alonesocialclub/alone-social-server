package social.alone.server.post.controller

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import social.alone.server.auth.oauth2.user.CurrentUser
import social.alone.server.controller.BaseController
import social.alone.server.post.domain.Post
import social.alone.server.post.dto.PostDto
import social.alone.server.post.service.PostCreateService
import social.alone.server.user.domain.User

@Controller
@RequestMapping(value = ["/api/posts"])
class PostController (val postCreateService: PostCreateService): BaseController() {
    
    @GetMapping("/{id}")
    fun getPost(
            @PathVariable("id") post: Post
    ): ResponseEntity<*> {
        return ResponseEntity.ok(post)
    }

    @PostMapping("/")
    fun createPost(
            @CurrentUser user: User,
            @RequestBody postDto: PostDto
    ): ResponseEntity<*> {
        return ResponseEntity.ok(postCreateService.create(user, postDto))
    }

}
