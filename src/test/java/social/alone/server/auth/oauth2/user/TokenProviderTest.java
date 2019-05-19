package social.alone.server.auth.oauth2.user;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import social.alone.server.BaseIntegrateTest;
import social.alone.server.user.User;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenProviderTest extends BaseIntegrateTest {

    @Autowired
    private TokenProvider tokenProvider;

    @Test
    public void test() {
        User user = createUser("email@email.com");
        String token = tokenProvider.createToken(user);
        assertThat(token).isNotEmpty();
    }

}