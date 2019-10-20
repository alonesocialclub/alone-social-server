package social.alone.server.post.repository

import org.springframework.data.jpa.repository.JpaRepository
import social.alone.server.post.domain.Post


interface PostRepository : JpaRepository<Post, String>
