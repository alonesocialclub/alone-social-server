package social.alone.server.link


import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import social.alone.server.event.domain.Event

@Service
@Transactional
class LinkService(val linkRepository: LinkRepository) {

    fun createLink(event: Event): Link {
        val link = event.createLink()
        return linkRepository.save(link)
    }
}
