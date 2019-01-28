package com.freestudy.api;

import com.freestudy.api.user.UserAfterSaveEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventListener implements ApplicationListener<UserAfterSaveEvent> {

  @Override
  public void onApplicationEvent(UserAfterSaveEvent event) {
    System.out.println("---------- onApplicationEvent user saved!! --------");
    System.out.println(event.getUser().getId());
    // TODO after save logic
  }
}
