package com.freestudy.api.config;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@Configuration
public class AppConfig {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Autowired
  public AppProperties appProperties;

  @Autowired
  private Environment environment;

  @Bean
  public ApplicationRunner applicationRunner() {
    return new ApplicationRunner() {
      @Override
      public void run(ApplicationArguments args) throws Exception {
        System.out.println("========================================");
        System.out.println("========================================");
      }
    };
  }
}
