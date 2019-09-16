package social.alone.server.auth.email

import javax.validation.constraints.Email

data class LoginRequestDto(
        @Email
        val email: String, var password: String
){

}