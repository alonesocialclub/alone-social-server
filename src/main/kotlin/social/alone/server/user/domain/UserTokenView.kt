package social.alone.server.user.domain

data class UserTokenView(val user: UserView, val token: String? = null) {
    constructor(user: User, token: String?) : this(user.view(), token)
    constructor(user: User) : this(user.view(), null)
}
