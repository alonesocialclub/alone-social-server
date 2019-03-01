package com.freestudy.api.infra.slack;

public interface SlackMessagable {
  SlackMessageEvent buildSlackMessageEvent(String message);
}
