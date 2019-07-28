package social.alone.server.config

import lombok.Data
import lombok.Getter
import lombok.Setter
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

import java.util.ArrayList

@ConfigurationProperties(prefix = "app")
@Component
@Getter
class AppProperties {

    val auth = Auth()
    val oauth2 = OAuth2()
    val link = Link()
    val slack = Slack()
    val sentry = Sentry()

    // TODO lombok fix
    class Auth {
        var tokenSecret: String? = null
        var tokenExpirationMsec: Long = 0
    }

    // TODO lombok fix
    @Data
    class OAuth2 {

        var clientId: String? = null
            set(clientId) {
                field = this.clientId
            }
        var clientSecret: String? = null
            set(clientSecret) {
                field = this.clientSecret
            }

        var authorizedRedirectUris: List<String> = ArrayList()
            set(authorizedRedirectUris) {
                field = this.authorizedRedirectUris
            }

    }

    @Data
    class Link {
        var host: String? = null
            set(host) {
                field = this.host
            }

    }

    @Data
    class Slack {
        var channel: String? = null
            set(channel) {
                field = this.channel
            }
    }


    @Data
    class Sentry {
        var dsn: String? = null
    }
}
