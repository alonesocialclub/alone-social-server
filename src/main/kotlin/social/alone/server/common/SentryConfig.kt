package social.alone.server.common

import io.sentry.Sentry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.servlet.HandlerExceptionResolver
import social.alone.server.common.config.AppProperties


@Configuration
@ConfigurationProperties(prefix = "app.sentry")
class SentryConfig {

    @Autowired
    lateinit var dsn: String

    @Bean
    @Profile("prod")
    fun sentryExceptionResolver(): HandlerExceptionResolver {
        Sentry.init(dsn)
        return io.sentry.spring.SentryExceptionResolver()
    }
}