package social.alone.server.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.util.*

@ConfigurationProperties(prefix = "app")
@Component
data class AppProperties(
        val auth:Auth = Auth(),
        val oauth2:OAuth2 = OAuth2(),
        val link:Link = Link(),
        val slack:Slack = Slack(),
        val sentry:Sentry = Sentry()
) {

    data class Auth (var tokenSecret: String? = null, var tokenExpirationMsec: Long = 3600000){
    }


    data class OAuth2 (
        val clientId: String? = null,
        val clientSecret: String? = null,
        val authorizedRedirectUris: List<String> = ArrayList()
    )

    data class Link (var host: String? = null)

    data class Slack(var channel: String? = null)

    data class Sentry(var dsn: String? = null)
}
