package com.freestudy.api.event.location;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;


@Embeddable
@Getter
@NoArgsConstructor
public class Location {

  public Location(String address, String name) {
    this.address = address;
    this.name = name;
  }

  @Builder
  public Location(
          String address,
          String name,
          Double longitude,
          Double latitude,
          String placeUrl
  ) {
    this.address = address;
    this.name = name;
    this.longitude = longitude;
    this.latitude = latitude;
    this.placeUrl = placeUrl;
    // TODO image url updqte
    this.imageUrl = "https://alone.social/cover-default.jpg";
  }

  private String address;

  private String name;

  private Double longitude;

  private Double latitude;

  private String placeUrl;

  private String imageUrl;
}
