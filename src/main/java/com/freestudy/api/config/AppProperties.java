package com.freestudy.api.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "app")
@Component
@Getter
public class AppProperties {

  private final Auth auth = new Auth();
  private final OAuth2 oauth2 = new OAuth2();
  private final Link link = new Link();

  // TODO lombok fix
  public static class Auth {
    private String tokenSecret;
    private long tokenExpirationMsec;

    public String getTokenSecret() {
      return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
      this.tokenSecret = tokenSecret;
    }

    public long getTokenExpirationMsec() {
      return tokenExpirationMsec;
    }

    public void setTokenExpirationMsec(long tokenExpirationMsec) {
      this.tokenExpirationMsec = tokenExpirationMsec;
    }
  }

  // TODO lombok fix
  @Data
  public static final class OAuth2 {

    private String clientId;
    private String clientSecret;

    private List<String> authorizedRedirectUris = new ArrayList<>();

    public List<String> getAuthorizedRedirectUris() {
      return authorizedRedirectUris;
    }

  }

  @Data
  public static final class Link {
    private String host;

  }

  public Auth getAuth() {
    return auth;
  }

  public OAuth2 getOauth2() {
    return oauth2;
  }
}
