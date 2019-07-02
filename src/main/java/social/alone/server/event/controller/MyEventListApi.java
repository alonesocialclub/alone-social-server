package social.alone.server.event.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import social.alone.server.auth.oauth2.user.CurrentUser;
import social.alone.server.event.Event;
import social.alone.server.event.service.EventSearchService;
import social.alone.server.event.type.EventQueryParams;
import social.alone.server.user.User;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping(value = "/api/events/my")
@RequiredArgsConstructor
public class MyEventListApi {

    private final EventSearchService eventSearchService;

    @GetMapping("/upcoming")
    public ResponseEntity upcoming(
            final Pageable pageable,
            @CurrentUser final User user,
            @Valid final EventQueryParams eventQueryParams
    ) {
        if (user == null){
            return ResponseEntity.badRequest().build();
        }

        Page<Event> page = this.eventSearchService.findAllBy(
                pageable,
                Optional.ofNullable(user),
                eventQueryParams
        );
        return ResponseEntity.ok(page);
    }

    @GetMapping("/past")
    public ResponseEntity past(
            final Pageable pageable,
            @CurrentUser final User user,
            @Valid final EventQueryParams eventQueryParams
    ) {

        if (user == null){
            return ResponseEntity.badRequest().build();
        }

        Page<Event> page = this.eventSearchService.findAllBy(
                pageable,
                Optional.ofNullable(user),
                eventQueryParams
        );
        return ResponseEntity.ok(page);
    }

}
