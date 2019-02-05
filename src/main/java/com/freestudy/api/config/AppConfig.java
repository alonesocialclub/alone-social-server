package com.freestudy.api.config;


import io.sentry.Sentry;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
public class AppConfig {

  @Autowired
  public AppProperties appProperties;

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  @Profile("prod")
  public HandlerExceptionResolver sentryExceptionResolver() {
    Sentry.init(appProperties.getSentry().getDsn());
    return new io.sentry.spring.SentryExceptionResolver();
  }
}
