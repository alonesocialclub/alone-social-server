package com.freestudy.api.event.location;


import com.freestudy.api.location.Location;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LocationTest {


  @Test
  public void test() {

    var l = new Location("도산대로", "할리스", 123.123, 123.123, "https://naver.com");

    assertThat(l.getImageUrl()).isNotNull();
    assertThat(l.getImageUrl()).contains("hollys");

  }
}