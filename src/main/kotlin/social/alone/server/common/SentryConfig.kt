package social.alone.server.common

import io.sentry.Sentry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.servlet.HandlerExceptionResolver
import social.alone.server.common.config.AppProperties


@Configuration
class SentryConfig {

    @Autowired
    lateinit var appProperties: AppProperties

    @Bean
    @Profile("prod")
    fun sentryExceptionResolver(): HandlerExceptionResolver {
        Sentry.init(appProperties.sentry.dsn)
        return io.sentry.spring.SentryExceptionResolver()
    }
}