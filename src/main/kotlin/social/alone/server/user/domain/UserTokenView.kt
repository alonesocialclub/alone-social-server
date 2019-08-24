package social.alone.server.user.domain

data class UserTokenView(val user: User, val token: String? = null) {

}
