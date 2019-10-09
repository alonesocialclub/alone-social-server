package social.alone.server.user.dto

import javax.validation.constraints.Size

data class ProfileDto(
    @field:Size(max = 20) val name: String? = null
)
