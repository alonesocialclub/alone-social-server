package social.alone.server.auth.email;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LoginRequestDto {

  @Email
  public String email;

  @NotEmpty
  public String password;

}
