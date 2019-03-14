package social.alone.server.infra.slack;

public interface SlackMessagable {
  SlackMessageEvent buildSlackMessageEvent(String message);
}
