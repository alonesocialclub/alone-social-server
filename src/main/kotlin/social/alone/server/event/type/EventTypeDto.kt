package social.alone.server.event.type

import lombok.*

import javax.validation.constraints.Size

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = ["value"])
class EventTypeDto(id: Long?, value: String) {

    var id: Long? = null

    @Size(min = 1, max = 30)
    var value: String? = null
}
