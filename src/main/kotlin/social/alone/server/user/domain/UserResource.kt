package social.alone.server.user.domain

data class UserResource(val user: User) {

    var  token: String? = null
        set(token) {
            field = this.token
        }

}
