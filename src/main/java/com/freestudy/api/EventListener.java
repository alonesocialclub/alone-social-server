package com.freestudy.api;

import com.freestudy.api.infra.slack.SlackNotifier;
import com.freestudy.api.user.User;
import com.freestudy.api.user.UserCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class EventListener implements ApplicationListener<UserCreateEvent> {

  @Autowired
  private SlackNotifier slackNotifier;

  @Override
  public void onApplicationEvent(UserCreateEvent event) {
    User user = event.getUser();
    var message = user.getName() + "님이 " + user.getProvider() + "를 통해 가입하셨습니다.";
    slackNotifier.send(message);
  }
}
