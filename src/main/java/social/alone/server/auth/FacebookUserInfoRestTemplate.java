package social.alone.server.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class FacebookUserInfoRestTemplate {

    private final RestTemplateBuilder restTemplateBuilder;

    private final FacebookConfig config;

    @Bean
    public RestTemplate get() {
        return restTemplateBuilder
                .rootUri(config.getGraphUrl())
                .additionalInterceptors(new FacebookUserInfoRestTemplate.Interceptor())
                .errorHandler(new FacebookUserInfoRestTemplate.ErrorHandler())
                .build();
    }

    class Interceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(
                HttpRequest request,
                byte[] body,
                ClientHttpRequestExecution execution) throws IOException {
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
