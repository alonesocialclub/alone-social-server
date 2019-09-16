package social.alone.server.config

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

import org.assertj.core.api.Assertions.assertThat


@SpringBootTest
@RunWith(SpringRunner::class)
class AppPropertiesTest {

    @Autowired
    lateinit var appProperties: AppProperties

    @Test
    fun testBean() {

        assertThat(appProperties).isNotNull
    }
}