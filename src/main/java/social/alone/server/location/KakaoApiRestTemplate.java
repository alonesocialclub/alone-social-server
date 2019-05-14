package social.alone.server.location;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import social.alone.server.common.config.KakaoConfig;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoApiRestTemplate {

    private final RestTemplateBuilder restTemplateBuilder;

    private final KakaoConfig kakaoConfig;

    @Bean
    public RestTemplate kakaoLocalSearchApi() {
        return restTemplateBuilder
                .rootUri(kakaoConfig.getSearchKeywordUrl())
                .additionalInterceptors(new Interceptor())
                .errorHandler(new ErrorHandler())
                .build();
    }

    class Interceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(
                HttpRequest request,
                byte[] body,
                ClientHttpRequestExecution execution) throws IOException {
            var headers = request.getHeaders();
            headers.set("Authorization", "KakaoAK " + kakaoConfig.getApiKey());
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

            return execution.execute(request, body);
        }
    }

    class ErrorHandler implements ResponseErrorHandler {

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return false;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            log.error(response.toString());
        }
    }
}
