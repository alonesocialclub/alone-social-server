package social.alone.server.validate

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.springframework.boot.jackson.JsonComponent
import org.springframework.validation.Errors

import java.io.IOException


@JsonComponent
class ErrorsSerializer : JsonSerializer<Errors>() {
    @Throws(IOException::class)
    override fun serialize(errors: Errors, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeStartArray()
        errors.fieldErrors.stream().forEach { e ->
            try {
                gen.writeStartObject()
                gen.writeStringField("field", e.field)
                gen.writeStringField("code", e.code)
                gen.writeStringField("objectName", e.objectName)
                gen.writeStringField("defaultMessage", e.defaultMessage)
                gen.writeEndObject()
            } catch (e1: IOException) {
                e1.printStackTrace()
            }
        }

        errors.globalErrors.forEach { e ->
            try {
                gen.writeStartObject()
                gen.writeStringField("code", e.code)
                gen.writeStringField("objectName", e.objectName)
                gen.writeStringField("defaultMessage", e.defaultMessage)
                gen.writeEndObject()
            } catch (e1: IOException) {
                e1.printStackTrace()
            }
        }
        gen.writeEndArray()
    }
}
