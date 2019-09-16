package social.alone.server.auth.email

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

data class SignUpRequestDto (

    @NotEmpty
    @Size(max = 20)
    val name: String,

    @NotEmpty
    @Size(min = 6, max = 20)
    val password: String,

    @NotEmpty
    @Email
    val email: String
)
