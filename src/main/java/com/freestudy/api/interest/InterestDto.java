package com.freestudy.api.interest;

import lombok.*;

import javax.validation.constraints.Size;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"value"})
public class InterestDto {

  @Size(min = 1, max = 30)
  String value;
}
