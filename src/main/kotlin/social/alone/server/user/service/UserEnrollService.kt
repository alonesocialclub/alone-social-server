package social.alone.server.user.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.Assert
import social.alone.server.auth.FacebookUserInfoFetcher
import social.alone.server.auth.anonymous.JoinByProfileRequest
import social.alone.server.auth.oauth2.user.FacebookOAuth2UserInfo
import social.alone.server.image.Image
import social.alone.server.image.ImageRepository
import social.alone.server.infrastructure.S3Uploader
import social.alone.server.user.domain.AuthProvider
import social.alone.server.user.domain.Profile
import social.alone.server.user.domain.User
import social.alone.server.user.repository.UserRepository
import java.util.*

@Service
@Transactional
class UserEnrollService(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder,
        private val facebookUserInfoFetcher: FacebookUserInfoFetcher,
        private val imageRepository: ImageRepository
) {

    fun enrollByEmailPassword(
            email: String, password: String, name: String
    ): User {
        Assert.notNull(email, "required")
        Assert.notNull(password, "required")
        Assert.notNull(name, "required")

        val user = User(email, passwordEncoder.encode(password), Profile(name))

        return userRepository.save(user)
    }

    fun byFacebook(accessToken: String): User {
        val userInfo = facebookUserInfoFetcher.getUserInfo(accessToken)
        return this.enrollByFacebook(userInfo)
    }

    private fun enrollByFacebook(
            userInfo: FacebookOAuth2UserInfo): User {
        val email = userInfo.email
        val byEmail = userRepository.findByEmailAndProvider(email, AuthProvider.facebook)
        return byEmail.orElseGet { userRepository.save(User(userInfo, AuthProvider.facebook)) }
    }

    fun byProfile(request: JoinByProfileRequest): User {
        val fakeEmail = request.profile.name + "@anonymous.com"
        val byEmail = userRepository.findByEmailAndProvider(fakeEmail, AuthProvider.facebook)
        if (byEmail.isPresent) {
            return byEmail.get()
        }

        val profile = requestToProfile(request)
        val user = User(fakeEmail, null, profile)
        return userRepository.save(user)
    }


    private fun requestToProfile(request: JoinByProfileRequest): Profile {
        val image = request.profile.image?.id?.let { imageRepository.findById(it) } ?: Optional.empty()
        return Profile(request.profile.name, image.orElse(null))
    }

}
