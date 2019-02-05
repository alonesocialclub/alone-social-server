package com.freestudy.api.infra.slack;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class SlackMessageEvent extends ApplicationEvent {

  @Getter
  private String message;

  /**
   * Create a new ApplicationEvent.
   *
   * @param source the object on which the event initially occurred (never {@code null})
   */
  public SlackMessageEvent(Object source, String message) {
    super(source);
    this.message = message;
  }
}
