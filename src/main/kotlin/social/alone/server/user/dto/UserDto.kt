package social.alone.server.user.dto


import lombok.AllArgsConstructor
import lombok.EqualsAndHashCode
import lombok.Getter
import social.alone.server.interest.InterestDto
import javax.validation.constraints.Email
import javax.validation.constraints.Size

@Getter
@AllArgsConstructor
@EqualsAndHashCode(exclude = ["interests"])
data class UserDto(@field:Size(max = 20) val name: String? = null, @field:Email val email: String? = null, @field:Size(max = 3) val interests: List<InterestDto>? = null) {

}
