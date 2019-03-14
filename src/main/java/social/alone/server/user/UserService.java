package social.alone.server.user;


import social.alone.server.auth.SignUpRequestDto;
import social.alone.server.common.exception.ResourceNotFoundException;
import social.alone.server.interest.InterestService;
import social.alone.server.oauth2.user.UserPrincipalAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService implements UserDetailsService {

  private UserRepository userRepository;

  private InterestService interestService;

  private PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepository userRepository, InterestService interestService, PasswordEncoder passwordEncoder) {

    this.userRepository = userRepository;
    this.interestService = interestService;
    this.passwordEncoder = passwordEncoder;
  }

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

  public User createLocalAuthUser(
          SignUpRequestDto signUpRequestDto
  ) {

    User user = User.builder()
            .name(signUpRequestDto.getName())
            .email(signUpRequestDto.getEmail())
            .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
            .build();

    return userRepository.save(user);
  }

  // TODO FIX
  public User update(User user_, UserDto userDto) {

    User user = userRepository.findById(user_.getId()).get();

    if (userDto.getInterests() != null) {
      var interests = interestService.saveAll(userDto.getInterests());
      user.setInterests(interests);
    }

    user.update(userDto);

    return user;
  }
}