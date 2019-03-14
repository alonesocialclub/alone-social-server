package social.alone.server.event.type;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"value"})
public class EventTypeDto {

  Long id;

  @Size(min = 1, max = 30)
  String value;
}
