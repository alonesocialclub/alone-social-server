package social.alone.server.event.type;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/event-types")
public class EventTypeController {

  private EventTypeRepository eventTypeRepository;

  @Autowired
  public EventTypeController(EventTypeRepository eventTypeRepository) {
    this.eventTypeRepository = eventTypeRepository;
  }

  @GetMapping
  public ResponseEntity queryEvents(
          Pageable pageable
  ) {
    Page<EventType> all = this.eventTypeRepository.findAll(pageable);
    return ResponseEntity.ok(all);
  }

}
