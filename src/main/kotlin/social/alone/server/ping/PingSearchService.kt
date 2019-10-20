package social.alone.server.ping

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import social.alone.server.post.repository.PostRepository


@Service
class PingSearchService(
        val postRepository: PostRepository,
        val pingRepository: PingRepository
){

    fun findByPostId(postId: String, pageable: Pageable): Page<Ping> {
        if (!postRepository.existsById(postId)){
            return Page.empty()
        }
        return pingRepository.findAll(QPing.ping.post.id.eq(postId), pageable)
    }
}