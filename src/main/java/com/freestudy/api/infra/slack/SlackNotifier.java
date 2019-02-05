package com.freestudy.api.infra.slack;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;

@Component
@Slf4j
public class SlackNotifier {

  private static String URL = "https://hooks.slack.com/services/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";

  private RestTemplate restTemplate;

  @Autowired
  public SlackNotifier(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public void send(String text) {
    var msg = new Message(text);
    restTemplate.postForEntity(URL, msg, String.class);
  }

  @AllArgsConstructor
  @Getter
  private static class Message {
    private String text;
  }
}
