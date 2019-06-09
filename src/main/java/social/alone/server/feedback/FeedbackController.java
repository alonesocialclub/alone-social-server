package social.alone.server.feedback;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import social.alone.server.auth.oauth2.user.CurrentUser;
import social.alone.server.common.infrastructure.slack.SlackNotifier;
import social.alone.server.user.User;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final SlackNotifier slackNotifier;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity postFeedback(
            @CurrentUser User user,
            @Valid @RequestBody FeedbackRequest req

    ){
        slackNotifier.send("user(" + user.getId() + ") feedback : " + req.getText());
        return ResponseEntity.noContent().build();
    }

}
