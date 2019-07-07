package social.alone.server.location

import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpRequest
import org.springframework.http.MediaType
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.client.ResponseErrorHandler
import org.springframework.web.client.RestTemplate
import social.alone.server.common.config.KakaoConfig

import java.io.IOException

@Slf4j
@Component
@RequiredArgsConstructor
class KakaoApiRestTemplate {

    private val restTemplateBuilder: RestTemplateBuilder? = null

    private val kakaoConfig: KakaoConfig? = null

    @Bean
    fun kakaoLocalSearchApi(): RestTemplate {
        return restTemplateBuilder!!
                .rootUri(kakaoConfig!!.searchKeywordUrl)
                .additionalInterceptors(Interceptor())
                .errorHandler(ErrorHandler())
                .build()
    }

    internal inner class Interceptor : ClientHttpRequestInterceptor {
        @Throws(IOException::class)
        override fun intercept(
                request: HttpRequest,
                body: ByteArray,
                execution: ClientHttpRequestExecution): ClientHttpResponse {
            val headers = request.headers
            headers.set("Authorization", "KakaoAK " + kakaoConfig!!.apiKey)
            headers.contentType = MediaType.APPLICATION_JSON_UTF8

            return execution.execute(request, body)
        }
    }

    internal inner class ErrorHandler : ResponseErrorHandler {

        @Throws(IOException::class)
        override fun hasError(response: ClientHttpResponse): Boolean {
            return false
        }

        @Throws(IOException::class)
        override fun handleError(response: ClientHttpResponse) {
            log.error(response.toString())
        }
    }
}
