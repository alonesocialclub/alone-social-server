package social.alone.server.user.domain

import social.alone.server.interest.Interest


data class UserView(
        val id: Long?,
        val profile: Profile
)

fun User.view() = UserView(
        id = id,
        profile = profile
)