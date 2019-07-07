package social.alone.server.location;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {

  public String address;
  public String name;
  public Double longitude;
  public Double latitude;
  public String placeUrl;

  public Location buildLocation() {
    return new Location(address, name, longitude, latitude, placeUrl);
  }
}