package social.alone.server.user.domain

import social.alone.server.interest.Interest


data class UserView(
        val id: Long?,
        val profile: Profile,
        val imageUrl: String,
        val interests: MutableSet<Interest>,
        val providers: MutableSet<AuthProvider>
)

fun User.view() = UserView(
        id = id,

        profile = profile,
        imageUrl = imageUrl ?: "",
        interests = interests,
        providers = hashSetOf(provider!!)
)