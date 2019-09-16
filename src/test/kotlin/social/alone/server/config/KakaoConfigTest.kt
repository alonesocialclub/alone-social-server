package social.alone.server.config


import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import social.alone.server.config.KakaoConfig

import org.assertj.core.api.Assertions.assertThat

@SpringBootTest
@RunWith(SpringRunner::class)
class KakaoConfigTest {

    @Autowired
    lateinit var config: KakaoConfig

    @Test
    fun test() {
        assertThat(config.apiKey).isNotEmpty()
        assertThat(config.searchKeywordUrl).isNotEmpty()
    }

}