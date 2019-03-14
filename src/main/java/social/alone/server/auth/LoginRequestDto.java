package social.alone.server.auth;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class LoginRequestDto {

  @Email
  public String email;

  @NotEmpty
  public String password;
}
