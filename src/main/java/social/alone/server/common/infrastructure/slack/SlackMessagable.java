package social.alone.server.common.infrastructure.slack;

public interface SlackMessagable {
  SlackMessageEvent buildSlackMessageEvent(String message);
}
