package social.alone.server.auth


import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import social.alone.server.auth.oauth2.user.FacebookOAuth2UserInfo

import org.assertj.core.api.Assertions.assertThat

@SpringBootTest
@RunWith(SpringRunner::class)
class FacebookUserInfoFetcherTest {

    @Autowired
    lateinit var userInfoFetcher: FacebookUserInfoFetcher

    @Test
    @Ignore
    fun test() {
        val accessToken = "foo"
        val user = userInfoFetcher.getUserInfo(accessToken)
        assertThat(user.email).isNotEmpty()
        assertThat(user.id).isNotEmpty()
        assertThat(user.name).isNotEmpty()
        assertThat(user.imageUrl).isNotEmpty()
    }

}