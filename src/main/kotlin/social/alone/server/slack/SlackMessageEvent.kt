package social.alone.server.slack

import lombok.Getter
import org.springframework.context.ApplicationEvent

class SlackMessageEvent
/**
 * Create a new ApplicationEvent.
 *
 * @param source the object on which the event initially occurred (never `null`)
 */
(source: Any, @field:Getter
val message: String) : ApplicationEvent(source)
