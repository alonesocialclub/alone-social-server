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

    @NotEmpty
    private String searchKeywordUrl;

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setSearchKeywordUrl(String searchKeywordUrl) {
        this.searchKeywordUrl = searchKeywordUrl;
    }
}
