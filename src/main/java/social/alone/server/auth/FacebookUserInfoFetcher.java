package social.alone.server.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social.alone.server.auth.oauth2.user.OAuth2UserInfo;
import social.alone.server.auth.oauth2.user.OAuth2UserInfoFactory;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class FacebookUserInfoFetcher {

    private final FacebookConfig config;

    private final FacebookUserInfoRestTemplate facebookUserInfoRestTemplate;

    public OAuth2UserInfo getUserInfo(String accessToken) {
        var client = facebookUserInfoRestTemplate.get();
        var attributes = client.getForObject("/me?access_token={access_token}&fields=id,first_name,middle_name,last_name,name,email,picture.width(250).height(250)", Map.class, accessToken);
        System.out.println(attributes);
        return OAuth2UserInfoFactory.getOAuth2UserInfo("facebook", attributes);
    }
}
