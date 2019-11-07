package social.alone.server.infrastructure

import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import social.alone.server.slack.SlackNotifier


@RunWith(SpringRunner::class)
@SpringBootTest
class SlackNotifierTest {

    @Autowired
    lateinit var slackNotifier: SlackNotifier

    @Test
    @Ignore
    fun test() {
        slackNotifier.send("Hello world")
    }
}