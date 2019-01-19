package com.freestudy.api.event;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
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
            ((TextNode) treeNode.get("name")).asText()
    );
  }
}
