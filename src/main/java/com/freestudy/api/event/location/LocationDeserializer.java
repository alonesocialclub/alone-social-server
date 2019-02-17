package com.freestudy.api.event.location;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class LocationDeserializer extends JsonDeserializer<Location> {
  @Override
  public Location deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
    TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);

    return new Location(
            ((TextNode) treeNode.get("address")).asText(),
            ((TextNode) treeNode.get("name")).asText(),
            ((DoubleNode) treeNode.get("longitude")).asDouble(),
            ((DoubleNode) treeNode.get("latitude")).asDouble(),
            ((TextNode) treeNode.get("placeUrl")).asText()
    );
//    return Location
//            .builder()
//            .address(((TextNode) treeNode.get("address")).asText())
//            .name(((TextNode) treeNode.get("name")).asText())
//            .latitude(((DoubleNode) treeNode.get("latitude")).asDouble())
//            .longitude(((DoubleNode) treeNode.get("longitude")).asDouble())
//            .placeUrl(((TextNode) treeNode.get("placeUrl")).asText())
//            .imageUrl(((TextNode) treeNode.get("imageUrl")).asText())
//            .build();
  }
}
