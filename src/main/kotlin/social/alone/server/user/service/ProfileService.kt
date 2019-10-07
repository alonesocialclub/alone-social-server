package social.alone.server.user.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import social.alone.server.user.repository.UserRepository
import social.alone.server.user.domain.User
import social.alone.server.user.domain.Profile
import social.alone.server.user.dto.ProfileDto

@Service
@Transactional
class ProfileService(private val userRepository: UserRepository){
    fun patch(user: User, profileDto: ProfileDto): User{
        val profile: Profile = user.profile
        if(profileDto.name !== null){
            profile.name = profileDto.name
        }
        user.profile = profile
        userRepository.save(user)
        return user
    }
}
