package com.freestudy.api.event.location;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;


@Embeddable
@Getter
@NoArgsConstructor
public class Location {

  final static String IMAGE_HOST = "https://alone.social";

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
    this.imageUrl = getImageUrlByName();
  }

  private String address;

  private String name;

  private Double longitude;

  private Double latitude;

  private String placeUrl;

  private String imageUrl;

  private String getImageUrlByName() {
    int idx = (int)(Math.random() * 3);
    String defaultImage = IMAGE_HOST + "/cafe/random/" + idx + ".jpg";

    if (this.name == null){
      return defaultImage;
    }

    if (this.name.contains("스타벅스")){
      return IMAGE_HOST + "/cafe/starbucks.jpg";
    }

    if (this.name.contains("이디야")){
      return IMAGE_HOST + "/cafe/ediya.jpg";
    }


    if (this.name.contains("할리스")){
      return IMAGE_HOST + "/cafe/hollys.jpg";
    }

    if (this.name.contains("폴바셋")){
      return IMAGE_HOST + "/cafe/paulbassett.jpg";
    }

    return defaultImage;
  }
}
