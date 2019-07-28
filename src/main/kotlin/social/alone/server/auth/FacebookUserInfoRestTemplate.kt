package social.alone.server.auth

import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.client.ResponseErrorHandler
import org.springframework.web.client.RestTemplate

import java.io.IOException

@Slf4j
@Component
class FacebookUserInfoRestTemplate {

    @Autowired
    lateinit var restTemplateBuilder: RestTemplateBuilder

    @Autowired
    lateinit var config: FacebookConfig

    @Bean
    fun get(): RestTemplate {
        return restTemplateBuilder
                .rootUri(config.graphUrl)
                .build()
    }

}
