package social.alone.server.location;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LocationDto {

  private String address;
  private String name;
  private Double longitude;
  private Double latitude;
  private String placeUrl;

  public Location buildLocation() {
    return new Location(address, name, longitude, latitude, placeUrl);
  }
}