package social.alone.server

import social.alone.server.user.domain.Profile
import social.alone.server.user.domain.User


fun makeUser(email: String = "email@email.com"): User {
    return User(email, "1234", Profile("local"))
}