package social.alone.server.slack

import org.springframework.context.annotation.Profile
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
@Profile("prod")
class SlackMessageEventListener(val slackNotifier: SlackNotifier) {

    @EventListener
    fun HandleSlackMessage(event: SlackMessageEvent) {
        val message = event.message
        slackNotifier.send(message)
    }
}
