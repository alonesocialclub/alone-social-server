package com.freestudy.api.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.Errors;

import java.io.IOException;


@JsonComponent // Errors 에 대해 부트에서 Errors 를 시리얼라이즈 할 수 있게 만든다.
public class ErrorsSerializer extends JsonSerializer<Errors> {
  @Override
  public void serialize(Errors errors, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeStartArray();
    errors.getFieldErrors().stream().forEach(e -> {
      try {
        gen.writeStartObject();
        gen.writeStringField("field", e.getField());
        gen.writeStringField("code", e.getCode());
        gen.writeStringField("objectName", e.getObjectName());
        gen.writeStringField("defaultMessage", e.getDefaultMessage());
        gen.writeEndObject();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    });

    errors.getGlobalErrors().forEach(e -> {
      try {
        gen.writeStartObject();
        gen.writeStringField("code", e.getCode());
        gen.writeStringField("objectName", e.getObjectName());
        gen.writeStringField("defaultMessage", e.getDefaultMessage());
        gen.writeEndObject();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    });
    gen.writeEndArray();
  }
}
