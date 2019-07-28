package social.alone.server.config

import lombok.Getter
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

import javax.validation.constraints.NotEmpty

@Getter
@Configuration
@ConfigurationProperties(prefix = "app.kakao")
class KakaoConfig {
    @NotEmpty
    var apiKey: String? = null

    @NotEmpty
    var searchKeywordUrl: String? = null
}
