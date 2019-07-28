//package social.alone.server.user;
//
//import social.alone.server.interest.Interest;
//import social.alone.server.interest.InterestRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import social.alone.server.user.repository.UserRepository;
//import social.alone.server.user.domain.User;
//
//import java.util.ArrayList;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//@RunWith(SpringRunner.class)
//@DataJpaTest
//public class UserRepositoryTest {
//
//  @Autowired
//  UserRepository userRepository;
//
//  @Autowired
//  InterestRepository interestRepository;
//
//  @Test
//  public void userSaveTest() {
//    // given
//    User user = new User("userSaveTest@test.com", name="foo")
//            .email("userSaveTest@test.com")
//            .name("foo")
//            .build();
//
//    // when
//    User result = userRepository.save(user);
//
//    // then
//    assertThat(user.getId()).isNotNull();
//    assertThat(result).isEqualTo(user);
//  }
//
//  @Test
//  public void userSaveWithInterestsTest() {
//    // given
//    Interest interest = new Interest("develop");
//    User user = User.builder()
//            .email("userSaveTest@test.com")
//            .name("foo")
//            .build();
//
//    // when
//    user.getInterests().add(interest);
//    interest.getUsers().add(user);
//    userRepository.save(user);
//
//    // then
//    Assertions.assertThat(user.getInterests()).contains(interest);
//    Assertions.assertThat(interest.getUsers()).contains(user);
//    // cascade persistent
//    assertThat(interest.getId()).isNotNull();
//  }
//}