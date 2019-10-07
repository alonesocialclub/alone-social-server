package social.alone.server.user.domain

data class UserTokenView(val user: UserView, val authToken: String? = null) {
    constructor(user: User, authToken: String?) : this(user.view(), authToken)
    constructor(user: User) : this(user.view(), null)
}
