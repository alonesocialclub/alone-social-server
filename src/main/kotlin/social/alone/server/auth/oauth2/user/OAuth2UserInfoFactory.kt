package social.alone.server.auth.oauth2.user


import social.alone.server.auth.oauth2.OAuth2AuthenticationProcessingException
import social.alone.server.user.domain.AuthProvider

object OAuth2UserInfoFactory {

    fun getOAuth2UserInfo(registrationId: String, attributes: Any): OAuth2UserInfo {
        return if (registrationId.equals(AuthProvider.google.toString(), ignoreCase = true)) {
            GoogleOAuth2UserInfo(attributes as Map<String, Any>)
        } else if (registrationId.equals(AuthProvider.facebook.toString(), ignoreCase = true)) {
            FacebookOAuth2UserInfo(attributes as Map<String, Any>)
        } else if (registrationId.equals(AuthProvider.github.toString(), ignoreCase = true)) {
            GithubOAuth2UserInfo(attributes as Map<String, Any>)
        } else {
            throw OAuth2AuthenticationProcessingException("Sorry! Login with $registrationId is not supported yet.")
        }
    }
}
