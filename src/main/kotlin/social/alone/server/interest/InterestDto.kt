package social.alone.server.interest

import lombok.*

import javax.validation.constraints.Size

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = ["value"])
class InterestDto(value: String) {

    @Size(min = 1, max = 30)
    var value: String? = null
        internal set

    companion object {

        fun of(value: String): InterestDto {
            return InterestDto(value)
        }
    }
}
