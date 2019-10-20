package social.alone.server.config

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.springframework.boot.jackson.JsonComponent
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@JsonComponent
class LocalDateTimeSerializer : JsonSerializer<LocalDateTime>() {
    val FORMATTER = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss '+0900'")


    override fun serialize(value: LocalDateTime, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeString(FORMATTER.format(value))
    }

}