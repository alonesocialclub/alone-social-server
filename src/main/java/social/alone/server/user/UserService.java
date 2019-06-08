package social.alone.server.user;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import social.alone.server.auth.oauth2.user.UserPrincipalAdapter;
import social.alone.server.common.exception.ResourceNotFoundException;
import social.alone.server.interest.Interest;
import social.alone.server.interest.InterestService;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  private final InterestService interestService;


  @Override
  public UserDetails loadUserByUsername(String email)
          throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new UsernameNotFoundException("User not found with email : " + email)
            );

    return UserPrincipalAdapter.create(user);
  }

  public UserDetails loadUserById(Long id) {
    User user = userRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("User", "id", id)
    );

    return UserPrincipalAdapter.create(user);
  }

  // TODO FIX
  public User update(User user_, UserDto userDto) {

    User user = userRepository.findById(user_.getId()).get();

    if (userDto.getInterests() != null) {
      HashSet<Interest> interests = interestService.saveAll(userDto.getInterests());
      user.setInterests(interests);
    }

    user.update(userDto);

    return user;
  }
}