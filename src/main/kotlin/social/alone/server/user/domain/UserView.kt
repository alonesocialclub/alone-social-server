package social.alone.server.user.domain

import social.alone.server.interest.Interest


data class UserView(
        val id: Long?,
        val name: String,
        val imageUrl: String,
        val interests: MutableSet<Interest>,
        val providers: MutableSet<AuthProvider>
)

const val DEFAULT_PROFILE = "https://alone-social-static-image.s3.ap-northeast-2.amazonaws.com/profile.png";

fun User.view() = UserView(
        id = id,
        name = name.orEmpty(),
        imageUrl = imageUrl ?: DEFAULT_PROFILE,
        interests = interests,
        providers = hashSetOf(provider!!)
)