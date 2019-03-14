package social.alone.server.interest;

import lombok.*;

import javax.validation.constraints.Size;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"value"})
public class InterestDto {

  public static InterestDto of(String value) {
    return new InterestDto(value);
  }

  @Size(min = 1, max = 30)
  String value;
}
