package social.alone.server.auth.oauth2.user

import io.jsonwebtoken.*
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import social.alone.server.config.AppProperties
import social.alone.server.user.domain.User
import java.util.*

@Service
class TokenProvider (val appProperties: AppProperties){

    fun createToken(authentication: Authentication): String {
        val userPrincipalAdapter = authentication.principal as UserPrincipalAdapter
        return token(userPrincipalAdapter)
    }

    fun createToken(user: User): String {
        val userPrincipalAdapter = UserPrincipalAdapter.create(user)
        return token(userPrincipalAdapter)
    }

    private fun token(userPrincipalAdapter: UserPrincipalAdapter): String {
        val now = Date()
        val expiryDate = Date(now.time + appProperties.auth.tokenExpirationMsec)

        return Jwts.builder()
                .setSubject((userPrincipalAdapter.id!!).toString())
                .setIssuedAt(Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, appProperties.auth.tokenSecret)
                .compact()
    }


    internal fun getUserIdFromToken(token: String): Long? {
        val claims = Jwts.parser()
                .setSigningKey(appProperties.auth.tokenSecret)
                .parseClaimsJws(token)
                .body

        return java.lang.Long.parseLong(claims.subject)
    }

    internal fun validateToken(authToken: String): Boolean {
        try {
            Jwts.parser().setSigningKey(appProperties.auth.tokenSecret).parseClaimsJws(authToken)
            return true
        } catch (ex: SignatureException) {
            logger.error("Invalid JWT signature")
        } catch (ex: MalformedJwtException) {
            logger.error("Invalid JWT token")
        } catch (ex: ExpiredJwtException) {
            logger.error("Expired JWT token")
        } catch (ex: UnsupportedJwtException) {
            logger.error("Unsupported JWT token")
        } catch (ex: IllegalArgumentException) {
            logger.error("JWT claims string is empty.")
        }

        return false
    }

    companion object {

        private val logger = LoggerFactory.getLogger(TokenProvider::class.java)
    }

}
