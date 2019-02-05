package com.freestudy.api.infra.slack;

public interface SlackMessagable {

  public SlackMessageEvent buildSlackMessageEvent();
}
