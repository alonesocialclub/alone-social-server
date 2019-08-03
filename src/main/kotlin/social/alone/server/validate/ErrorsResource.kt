package social.alone.server.validate

import org.springframework.hateoas.Link
import org.springframework.validation.Errors

class ErrorsResource(content: Errors, vararg links: Link) {
    init {
    }
}
