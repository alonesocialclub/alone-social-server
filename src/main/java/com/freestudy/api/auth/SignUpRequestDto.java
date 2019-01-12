package com.freestudy.api.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {

  @NotEmpty
  @Size(max = 20)
  public String name;

  @NotEmpty
  @Size(min = 6, max = 20)
  public String password;

  @NotEmpty
  @Email
  public String email;
}
