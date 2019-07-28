package social.alone.server.user.domain

import lombok.Getter
import lombok.Setter
import org.springframework.hateoas.Link

class UserResource(user: User, vararg links: Link) {

    @Setter
    @Getter
    var token: String? = null
        set(token) {
            field = this.token
        }

}
