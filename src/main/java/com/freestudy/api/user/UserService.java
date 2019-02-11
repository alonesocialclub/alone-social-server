package com.freestudy.api.user;


import com.freestudy.api.auth.SignUpRequestDto;
import com.freestudy.api.common.exception.ResourceNotFoundException;
import com.freestudy.api.interest.InterestService;
import com.freestudy.api.oauth2.user.UserPrincipalAdapter;
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