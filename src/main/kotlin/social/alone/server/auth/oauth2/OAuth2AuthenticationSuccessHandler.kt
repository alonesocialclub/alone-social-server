package social.alone.server.auth.oauth2

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import social.alone.server.auth.oauth2.user.TokenProvider
import social.alone.server.config.AppProperties
import social.alone.server.exception.BadRequestException
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class OAuth2AuthenticationSuccessHandler @Autowired
internal constructor(private val tokenProvider: TokenProvider, private val appProperties: AppProperties,
                     private val environment: Environment,
                     private val httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository) : SimpleUrlAuthenticationSuccessHandler() {

    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        val targetUrl = determineTargetUrl(request, response, authentication)

        if (response.isCommitted) {
            logger.debug("Response has already been committed. Unable to redirect to $targetUrl")
            return
        }

        clearAuthenticationAttributes(request, response)
        redirectStrategy.sendRedirect(request, response, targetUrl)
    }

    protected fun determineTargetUrl(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication): String {
        val redirectUri = CookieUtils.getCookie(request, HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
                .map<String>({ it.value })

        if (redirectUri.isPresent && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw BadRequestException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication")
        }

        val targetUrl = redirectUri.orElse(defaultTargetUrl)

        val token = tokenProvider.createToken(authentication)

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .build().toUriString()
    }

    protected fun clearAuthenticationAttributes(request: HttpServletRequest, response: HttpServletResponse) {
        super.clearAuthenticationAttributes(request)
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response)
    }

    private fun isAuthorizedRedirectUri(uri: String): Boolean {
        return true;

        // TODO FIX
        // 프로덕션이 아닌경우 redirect uri 를 느슨하게 가져간다.
        // CHECK AFTER DEPLOY: profile 이 prod 일지 + type safe 하게 변경
//        val clientRedirectUri = URI.create(uri)
//        return appProperties.oauth2.authorizedRedirectUris
//                .stream()
//                .anyMatch { authorizedRedirectUri ->
//                     Only validate host and port. Let the clients use different paths if they want to
//                    val authorizedURI = URI.create(authorizedRedirectUri)
//                    if (authorizedURI.host.equals(clientRedirectUri.host, ignoreCase = true) && authorizedURI.port == clientRedirectUri.port) {
//                         TODO 살려내야함
//                        return@appProperties.getOauth2().getAuthorizedRedirectUris()
//                                .stream()
//                                .anyMatch true
//                    }
//                    false
//                }

    }
}
