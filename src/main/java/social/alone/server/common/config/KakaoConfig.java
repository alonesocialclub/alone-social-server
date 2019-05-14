package social.alone.server.common.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotEmpty;

@Getter
@Configuration
@ConfigurationProperties(prefix = "app.kakao")
public class KakaoConfig {
    @NotEmpty
    private String apiKey;

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
