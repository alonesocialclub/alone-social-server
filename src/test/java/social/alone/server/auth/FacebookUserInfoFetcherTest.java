package social.alone.server.auth;


import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import social.alone.server.auth.oauth2.user.FacebookOAuth2UserInfo;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FacebookUserInfoFetcherTest {

    @Autowired
    private FacebookUserInfoFetcher userInfoFetcher;

    @Test
    @Ignore
    public void test() {
        String accessToken = "foo";
        FacebookOAuth2UserInfo user = userInfoFetcher.getUserInfo(accessToken);
        assertThat(user.getEmail()).isNotEmpty();
        assertThat(user.getId()).isNotEmpty();
        assertThat(user.getName()).isNotEmpty();
        assertThat(user.getImageUrl()).isNotEmpty();
    }

}