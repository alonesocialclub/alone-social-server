package social.alone.server.event.location;


import social.alone.server.location.Location;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LocationTest {


  @Test
  public void test() {

    var l = new Location("도산대로", "할리스", 123.123, 123.123, "https://naver.com");
    assertThat(l.getImageUrl()).isNotNull();
    assertThat(l.getImageUrl()).contains("hollys");
  }

  @Test
  public void ofTest() {
    var l = Location.of("도산대로", "할리스", 123.123, 123.123);
    assertThat(l.getPlaceUrl()).isNull();
  }

  @Test
  public void imageUrlByName() {
    var l = Location.of("도산대로", "할리스", 123.123, 123.123);
    assertThat(l.getImageUrl()).contains("cafe/hollys.jpg");
  }
}