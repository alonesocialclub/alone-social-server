package social.alone.server.auth.email

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
class SignUpRequestDto {

    @NotEmpty
    @Size(max = 20)
    lateinit var name: String

    @NotEmpty
    @Size(min = 6, max = 20)
    lateinit var password: String

    @NotEmpty
    @Email
    lateinit var email: String
}
