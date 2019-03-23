package social.alone.server.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import social.alone.server.event.Event;
import social.alone.server.event.EventQueryParams;
import social.alone.server.event.EventSearchService;
import social.alone.server.user.User;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping(value = "/api/users/{userId}/events")
@RequiredArgsConstructor
public class UserEventController {

  final private EventSearchService eventSearchService;

  @GetMapping()
  public ResponseEntity getEventsWithUser(
          Pageable pageable,
          @PathVariable("userId") User user,
          @Valid final EventQueryParams eventQueryParams
  ) {
    Page<Event> page = eventSearchService.findAllBy(pageable, Optional.ofNullable(user), eventQueryParams);
    return ResponseEntity.ok(page);
  }

}
