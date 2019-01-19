package com.freestudy.api.user;


import com.freestudy.api.interest.InterestDto;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"interests"})
public class UserDto {

  @Size(max = 20)
  private String name;

  @Email
  private String email;

  @Size(max = 3)
  private List<InterestDto> interests;

}
