package com.freestudy.api.event.location;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class LocationSerializer extends JsonSerializer<Location> {

  @Override
  public void serialize(Location location, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeStartObject();
    gen.writeStringField("address", location.getAddress());
    gen.writeStringField("name", location.getName());
    gen.writeStringField("placeUrl", location.getPlaceUrl());
    gen.writeStringField("imageUrl", location.getImageUrl());
    gen.writeNumberField("longitude", location.getLongitude());
    gen.writeNumberField("latitude", location.getLatitude());
    gen.writeEndObject();
  }
}
