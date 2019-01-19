package com.freestudy.api.event;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {

  @NotEmpty
  private String name;

  @NotEmpty
  private String description;

  private Location location;

  @NotNull
  private LocalDateTime startedAt;

  @NotNull
  private LocalDateTime endedAt;

  @Min(0)
  private int limitOfEnrollment;
}
