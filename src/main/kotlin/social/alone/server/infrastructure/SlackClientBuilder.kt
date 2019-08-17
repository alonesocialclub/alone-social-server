package social.alone.server.infrastructure


import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpRequest
import org.springframework.http.MediaType
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.client.ResponseErrorHandler
import org.springframework.web.client.RestTemplate
import java.io.IOException

@Component
class SlackClientBuilder(
        val restTemplateBuilder: RestTemplateBuilder
) {


    @Bean
    fun slackClient(): RestTemplate {

        return restTemplateBuilder
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
            println(response)
        }
    }
}
