package social.alone.server.event.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import social.alone.server.common.controller.BaseController;
import social.alone.server.event.Event;
import social.alone.server.event.EventQueryParams;
import social.alone.server.event.EventSearchService;
import social.alone.server.oauth2.user.CurrentUser;
import social.alone.server.user.User;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping(value = "/api/events")
@AllArgsConstructor
public class EventSearchController extends BaseController {

  private final EventSearchService eventSearchService;

  @GetMapping
  public ResponseEntity queryEvents(
          final Pageable pageable,
          @CurrentUser final User user,
          @Valid final EventQueryParams eventQueryParams
  ) {
    Page<Event> page = this.eventSearchService.findAllBy(
            pageable,
            Optional.ofNullable(user),
            eventQueryParams
    );
    return ResponseEntity.ok(page);
  }

  @GetMapping("/{id}")
  public ResponseEntity getEvent(
          @PathVariable("id") Event event
  ) {
    if (event == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(event);
  }
}
