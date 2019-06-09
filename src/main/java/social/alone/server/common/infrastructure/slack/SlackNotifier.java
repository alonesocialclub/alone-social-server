package social.alone.server.common.infrastructure.slack;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import social.alone.server.common.config.AppProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
    if (Arrays.asList(env.getActiveProfiles()).contains("test")){
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
