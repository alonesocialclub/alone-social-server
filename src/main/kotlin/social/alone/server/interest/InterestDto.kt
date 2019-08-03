package social.alone.server.interest

import lombok.EqualsAndHashCode
import javax.validation.constraints.Size

@EqualsAndHashCode(of = ["value"])
data class InterestDto(
        @Size(min = 1, max = 30)
        val value: String
) {

    companion object {

        fun of(value: String): InterestDto {
            return InterestDto(value)
        }
    }
}
