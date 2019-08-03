package social.alone.server.auth

import lombok.Getter
import lombok.Setter
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.facebook")
class FacebookConfig {

    lateinit var graphUrl: String

}
