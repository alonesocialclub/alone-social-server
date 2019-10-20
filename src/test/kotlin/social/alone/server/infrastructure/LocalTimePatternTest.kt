package social.alone.server.infrastructure

import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class LocalTimePatternTest {

    @Test
    fun test() {
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss Z")
        print(ZonedDateTime.now().format(formatter))
    }

    @Test
    fun test_localDateTime_noe() {
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss Z")
        var zone = ZoneId.of("Asia/Seoul")
        print(LocalDateTime.now().atZone(zone).format(formatter))
    }
}