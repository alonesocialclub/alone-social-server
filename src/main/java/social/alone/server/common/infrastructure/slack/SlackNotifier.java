package social.alone.server.common.infrastructure.slack;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import social.alone.server.common.config.AppProperties;

import java.util.Arrays;

@Component
@Slf4j
@RequiredArgsConstructor
public class SlackNotifier {

  private final AppProperties appProperties;

  private final RestTemplate restTemplate;

  private final Environment env;


  @Async
  public void send(String text) {
    if (!Arrays.asList(env.getActiveProfiles()).contains("prod")){
      return;
    }
    String url = appProperties.getSlack().getChannel();
    Message msg = new Message(text);
    restTemplate.postForEntity(url, msg, String.class);
  }

  @AllArgsConstructor
  @Getter
  private static class Message {
    private String text;
  }
}
