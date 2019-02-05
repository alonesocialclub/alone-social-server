package com.freestudy.api.infra.slack;

import com.freestudy.api.config.AppProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class SlackNotifier {

  private AppProperties appProperties;

  private RestTemplate restTemplate;

  @Autowired
  public SlackNotifier(AppProperties appProperties, RestTemplate restTemplate) {
    this.appProperties = appProperties;
    this.restTemplate = restTemplate;
  }

  @Async
  public void send(String text) {
    var url = appProperties.getSlack().getChannel();
    var msg = new Message(text);
    restTemplate.postForEntity(url, msg, String.class);
  }

  @AllArgsConstructor
  @Getter
  private static class Message {
    private String text;
  }
}
