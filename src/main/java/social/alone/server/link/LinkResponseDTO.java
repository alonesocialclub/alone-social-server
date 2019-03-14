package social.alone.server.link;

import social.alone.server.event.Event;
import lombok.Data;

@Data
public class LinkResponseDTO {

  Integer id;

  String url;

  Event event;
}
