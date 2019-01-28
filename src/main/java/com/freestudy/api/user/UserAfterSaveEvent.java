package com.freestudy.api.user;

import org.springframework.context.ApplicationEvent;

public class UserAfterSaveEvent extends ApplicationEvent {
  private final User user;

  /**
   * Create a new ApplicationEvent.
   *
   * @param source the object on which the event initially occurred (never {@code null})
   */
  public UserAfterSaveEvent(Object source) {
    super(source);
    this.user = (User) source;
  }

  public User getUser() {
    return user;
  }
}
