package social.alone.server.auth.oauth2.user

import org.springframework.security.core.annotation.AuthenticationPrincipal
import java.lang.annotation.Documented

@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@Documented
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : user")
annotation class CurrentUser