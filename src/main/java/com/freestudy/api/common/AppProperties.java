package com.freestudy.api.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@Component
@ConfigurationProperties("custom")
@Getter
@Setter
public class AppProperties {

  @NotEmpty
  private String adminUsername;

  @NotEmpty
  private String adminPassword;

  @NotEmpty
  private String oauthClientId;

  @NotEmpty
  private String oauthClientSecret;
}
