package social.alone.server.auth.oauth2.user

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User
import social.alone.server.user.domain.User
import social.alone.server.user.domain.UserRole

class UserPrincipalAdapter(val user: User) : OAuth2User, UserDetails {
    val id: String = user.id
    val email: String? = user.email
    private val password: String? = user.password
    private val authorities: Collection<GrantedAuthority> = authorities(user.roles)
    private var attributes: Map<String, Any>? = null

    override fun getPassword(): String? {
        return password
    }

    override fun getUsername(): String? {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun getAttributes(): Map<String, Any>? {
        return attributes
    }

    fun setAttributes(attributes: Map<String, Any>) {
        this.attributes = attributes
    }

    override fun getName(): String {
        return id.toString()
    }

    companion object {

        fun create(user: User): UserPrincipalAdapter {
            return UserPrincipalAdapter(user)
        }

        fun create(user: User, attributes: Map<String, Any>): UserPrincipalAdapter {
            val userPrincipalAdapter = UserPrincipalAdapter.create(user)
            userPrincipalAdapter.setAttributes(attributes)
            return userPrincipalAdapter
        }

        private fun authorities(roles: Set<UserRole>): Collection<GrantedAuthority> {
            return roles.map { r -> SimpleGrantedAuthority("ROLE_" + r.name) }
        }
    }
}
