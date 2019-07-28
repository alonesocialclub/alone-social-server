package social.alone.server.slack

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.core.env.Environment
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import social.alone.server.config.AppProperties
import java.util.*

@Component
@Slf4j
@RequiredArgsConstructor
class SlackNotifier {

    private val appProperties: AppProperties? = null

    private val restTemplate: RestTemplate? = null

    private val env: Environment? = null


    @Async
    fun send(text: String) {
        if (!Arrays.asList(*env!!.activeProfiles).contains("prod")) {
            return
        }
        val url = appProperties!!.slack.channel
        val msg = Message(text)
//        restTemplate!!.postForEntity(url, msg, String::class.java)
    }

    @AllArgsConstructor
    @Getter
    private class Message(text: String) {
    }
}
