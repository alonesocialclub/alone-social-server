package social.alone.server.user.dto


import social.alone.server.interest.InterestDto
import javax.validation.constraints.Email
import javax.validation.constraints.Size

data class UserDto(
        @field:Size(max = 20) val name: String? = null,
        @field:Email val email: String? = null,
        @field:Size(max = 3) val interests: List<InterestDto>? = null
)
