package social.alone.server.credentials.dto

import lombok.Getter
import javax.validation.constraints.NotEmpty

@Getter
class AppleLoginDto {
    @NotEmpty
    lateinit var userIdentifier: String

    @NotEmpty
    lateinit var authorizationCode: String
}
