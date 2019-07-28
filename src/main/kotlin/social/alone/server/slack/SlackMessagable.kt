package social.alone.server.slack

interface SlackMessagable {
    fun buildSlackMessageEvent(message: String): SlackMessageEvent
}
