package social.alone.server.event


import social.alone.server.location.Location
import org.junit.Test

import org.assertj.core.api.Assertions.assertThat

class LocationTest {


    @Test
    fun test() {
        val l = Location("도산대로", "할리스", 123.123, 123.123, "https://naver.com")
        assertThat(l.imageUrl).isNotNull()
        assertThat(l.imageUrl).contains("hollys")
    }
}
