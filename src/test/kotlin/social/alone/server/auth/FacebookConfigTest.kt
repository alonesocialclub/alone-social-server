package social.alone.server.auth

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

import org.assertj.core.api.Assertions.assertThat


@SpringBootTest
@RunWith(SpringRunner::class)
class FacebookConfigTest {

    @Autowired
    lateinit var config: FacebookConfig

    @Test
    fun test() {
        assertThat(config.graphUrl).isEqualTo("https://graph.facebook.com/v3.3")
    }

}