package social.alone.server.auth

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import social.alone.server.auth.oauth2.user.TokenProvider
import social.alone.server.user.domain.User

@Service
class AuthTokenGenerator(val tokenProvider: TokenProvider, val authenticationManager: AuthenticationManager) {

    fun byEmailPassword(email: String, password: String): String {
        val authentication = authenticationManager!!.authenticate(
                UsernamePasswordAuthenticationToken(email, password)
        )

        val token = tokenProvider!!.createToken(authentication)
        SecurityContextHolder.getContext().authentication = authentication

        return token
    }

    fun byUser(user: User): String {
        return tokenProvider!!.createToken(user)
    }
}
