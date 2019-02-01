package com.freestudy.api.event.location;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;


@Embeddable
@Getter
@NoArgsConstructor
public class Location {

  public Location(String address, String name) {
    this.address = address;
    this.name = name;
  }


  @NotNull
  private String address;

  @NotNull
  private String name;
}
