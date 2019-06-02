package social.alone.server.auth;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class FacebookLoginDto {

    @NotEmpty
    private String facebookAccessToken;
}
