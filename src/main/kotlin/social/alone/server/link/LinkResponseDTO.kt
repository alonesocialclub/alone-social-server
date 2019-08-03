package social.alone.server.link

import social.alone.server.event.domain.Event
import lombok.Data

@Data
class LinkResponseDTO {

    internal var id: Int? = null

    internal var url: String? = null

    internal var event: Event? = null
}
