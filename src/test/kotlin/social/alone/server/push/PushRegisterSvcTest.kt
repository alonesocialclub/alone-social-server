package social.alone.server.push

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest
class PushRegisterSvcTest {

    @Autowired
    lateinit var pushRegisterSvc: FcmTokenRegisterSvc;

    @Test
    fun test() {
        assertThat(pushRegisterSvc).isNotNull
    }
}