package social.alone.server.slack

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.extern.slf4j.Slf4j
import org.springframework.core.env.Environment
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import social.alone.server.config.AppProperties
import java.util.*

@Component
@Slf4j
class SlackNotifier(private val env: Environment,
                    private val appProperties: AppProperties
) {


    @Async
    fun send(text: String) {
        if (!Arrays.asList(*env.activeProfiles).contains("prod")) {
            return
        }
        val url = appProperties.slack.channel
        val msg = Message(text)
        //  TODO slack rest bean 만들어야 함
//        restTemplate.postForEntity(url!!, msg, String::class.java)
    }

    @AllArgsConstructor
    @Getter
    private class Message(text: String) {
    }
}
