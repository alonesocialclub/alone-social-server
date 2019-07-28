package social.alone.server.auth

import lombok.Getter

import javax.validation.constraints.NotEmpty

@Getter
class FacebookLoginDto {

    @NotEmpty
    lateinit var facebookAccessToken: String
}
