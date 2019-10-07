package social.alone.server.user.domain


data class UserView(
        val id: String,
        val profile: Profile
)

fun User.view() = UserView(
        id = id.toString(),
        profile = profile
)