package social.alone.server.slack

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
@Profile("prod")
class SlackMessageEventListener {

    @Autowired
    private val slackNotifier: SlackNotifier? = null

    @EventListener
    fun HandleSlackMessage(event: SlackMessageEvent) {
        val message = event.message
        slackNotifier!!.send(message)
    }
}
