package social.alone.server.event.type;


import lombok.Getter;

@Getter
public class Coordinate {

  final Double longitude;
  final Double latitude;

  public Coordinate(Double longitude, Double latitude) {
    this.longitude = longitude;
    this.latitude = latitude;
  }

}
