package social.alone.server

import io.sentry.Sentry
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.servlet.HandlerExceptionResolver


@Configuration
@ConfigurationProperties(prefix = "app.sentry")
class SentryConfig {

    lateinit var dsn: String

    @Bean
    @Profile("prod")
    fun sentryExceptionResolver(): HandlerExceptionResolver {
        Sentry.init(dsn)
        return io.sentry.spring.SentryExceptionResolver()
    }
}