package social.alone.server.push;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import social.alone.server.auth.oauth2.user.CurrentUser;
import social.alone.server.user.User;

import java.util.Optional;

@Controller
@RequestMapping(value = "/api/push")
@RequiredArgsConstructor
public class PushController {

    private final FcmTokenRegisterSvc fcmTokenRegisterSvc;

    @PostMapping("/tokens")
    public ResponseEntity registerToken(
            @RequestBody FcmTokenRequest req,
            @CurrentUser User user
    ) {

        FcmToken fcmToken = fcmTokenRegisterSvc.register(req.getFcmToken(), Optional.ofNullable(user));
        return ResponseEntity.noContent().build();
    }
}
