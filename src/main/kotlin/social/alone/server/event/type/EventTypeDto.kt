package social.alone.server.event.type

import javax.validation.constraints.Size

data class EventTypeDto(val id: Long? = null, @Size(min = 1, max = 30) val value: String) {

}
