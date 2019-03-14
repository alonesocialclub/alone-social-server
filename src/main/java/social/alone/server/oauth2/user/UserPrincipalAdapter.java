package social.alone.server.oauth2.user;

import social.alone.server.user.User;
import social.alone.server.user.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;
import java.util.stream.Collectors;

public class UserPrincipalAdapter implements OAuth2User, UserDetails {
  private Long id;
  private String email;
  private String password;
  private User user;
  private Collection<? extends GrantedAuthority> authorities;
  private Map<String, Object> attributes;

  public UserPrincipalAdapter(User user) {

    this.id = user.getId();
    this.email = user.getEmail();
    this.password = user.getPassword();
    this.authorities = authorities(user.getRoles());
    this.user = user;
  }

  public static UserPrincipalAdapter create(User user) {
    return new UserPrincipalAdapter(user);
  }

  public static UserPrincipalAdapter create(User user, Map<String, Object> attributes) {
    UserPrincipalAdapter userPrincipalAdapter = UserPrincipalAdapter.create(user);
    userPrincipalAdapter.setAttributes(attributes);
    return userPrincipalAdapter;
  }

  private static Collection<? extends GrantedAuthority> authorities(Set<UserRole> roles) {
    return roles.stream()
            .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
            .collect(Collectors.toSet());
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  @Override
  public String getName() {
    return String.valueOf(id);
  }

  public User getUser(){
    return user;
  }
}
