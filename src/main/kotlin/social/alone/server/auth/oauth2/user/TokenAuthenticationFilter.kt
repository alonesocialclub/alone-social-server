package social.alone.server.auth.oauth2.user

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import social.alone.server.user.service.CustomUserDetailService
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TokenAuthenticationFilter : OncePerRequestFilter() {

    @Autowired
    lateinit var tokenProvider: TokenProvider

    @Autowired
    lateinit var userService: CustomUserDetailService

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            getJwtFromRequest(request)?.let {jwt ->
                if (validateToken(jwt)) {
                    val userId = tokenProvider.getUserIdFromToken(jwt)
                    val userDetails = userService.loadUserById(userId)
                    val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authentication
                }
            }
        } catch (ex: Exception) {
            logger.error("Could not update user authentication in security context", ex)
        }

        filterChain.doFilter(request, response)
    }

    private fun validateToken(jwt: String): Boolean {
        return StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)
    }


    private fun getJwtFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7, bearerToken.length)
        } else null
    }

    companion object {

        private val logger = LoggerFactory.getLogger(TokenAuthenticationFilter::class.java)
    }
}
