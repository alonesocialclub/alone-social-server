package social.alone.server.link


import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import social.alone.server.event.domain.Event

@Service
@Transactional
@RequiredArgsConstructor
class LinkService {

    private val linkRepository: LinkRepository? = null

    fun createLink(event: Event): Link {
        val link = event.createLink()
        return linkRepository!!.save(link)
    }
}
