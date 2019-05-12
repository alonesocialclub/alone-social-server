package social.alone.server.event.type;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/event-types")
@RequiredArgsConstructor
public class EventTypeController {

  private final EventTypeRepository eventTypeRepository;

  @GetMapping
  public ResponseEntity queryEvents(
          Pageable pageable
  ) {
    Page<EventType> all = this.eventTypeRepository.findAll(pageable);
    return ResponseEntity.ok(all);
  }

}
