package social.alone.server.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social.alone.server.auth.oauth2.user.FacebookOAuth2UserInfo;
import social.alone.server.auth.oauth2.user.OAuth2UserInfoFactory;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class FacebookUserInfoFetcher {

    private final FacebookUserInfoRestTemplate facebookUserInfoRestTemplate;

    public FacebookOAuth2UserInfo getUserInfo(String accessToken) {
        var client = facebookUserInfoRestTemplate.get();
        var attributes = client.getForObject("/me?access_token={access_token}&fields=id,first_name,middle_name,last_name,name,email,picture.width(250).height(250)", Map.class, accessToken);
        return (FacebookOAuth2UserInfo) OAuth2UserInfoFactory.getOAuth2UserInfo("facebook", attributes);
    }
}
