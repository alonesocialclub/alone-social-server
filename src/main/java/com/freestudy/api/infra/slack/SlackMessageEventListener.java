package com.freestudy.api.infra.slack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class SlackMessageEventListener {

  @Autowired
  private SlackNotifier slackNotifier;

  @EventListener
  public void HandleSlackMessage(SlackMessageEvent event) {
    var message = event.getMessage();
    slackNotifier.send(message);
  }
}
