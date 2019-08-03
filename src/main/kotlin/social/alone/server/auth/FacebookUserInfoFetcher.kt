package social.alone.server.auth


import org.springframework.stereotype.Service
import social.alone.server.auth.oauth2.user.FacebookOAuth2UserInfo
import social.alone.server.auth.oauth2.user.OAuth2UserInfoFactory

@Service
class FacebookUserInfoFetcher(val facebookUserInfoRestTemplate: FacebookUserInfoRestTemplate) {

    fun getUserInfo(accessToken: String): FacebookOAuth2UserInfo {
        val client = facebookUserInfoRestTemplate.get()
        val attributes = client.getForObject("/me?access_token={access_token}&fields=id,first_name,middle_name,last_name,name,email,picture.width(250).height(250)", Map::class.java, accessToken)
        return OAuth2UserInfoFactory.getOAuth2UserInfo("facebook", attributes as Any) as FacebookOAuth2UserInfo
    }
}
