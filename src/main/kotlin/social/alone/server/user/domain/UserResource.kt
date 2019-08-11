package social.alone.server.user.domain

data class UserResource(val user: User, val token: String? = null) {}
