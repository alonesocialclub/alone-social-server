package com.freestudy.api.link;

import com.freestudy.api.event.Event;
import lombok.Data;

@Data
public class LinkResponseDTO {

  Integer id;

  String url;

  Event event;
}
