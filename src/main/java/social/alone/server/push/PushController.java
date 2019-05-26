package social.alone.server.push;

import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import social.alone.server.auth.oauth2.user.CurrentUser;
import social.alone.server.user.User;

@Controller
@RequestMapping(value = "/api/push")
@RequiredArgsConstructor
public class PushController {

    private final FcmTokenRepository fcmTokenRepository;

    @PostMapping("/tokens")
    public ResponseEntity enrollFcmToken(
            @RequestBody FcmTokenRequest req,
            @CurrentUser User user
    ) {

        fcmTokenRepository.save(new FcmToken(req.getFcmToken()));

        // See documentation on defining a message payload.
        Message message = Message.builder()
                .setApnsConfig(
                        ApnsConfig
                                .builder()
                                .setAps(
                                        Aps
                                                .builder()
                                                .setAlert(ApsAlert.builder().setBody("body").build())
                                                .build()
                                )
                                .build()
                )
                .setNotification(new Notification("title~!!", "body~"))
                .setToken(req.getFcmToken())
                .build();


        try {
            String response = FirebaseMessaging.getInstance().send(message);
            return ResponseEntity.ok(response);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return ResponseEntity.ok("foo");
        }
    }
}
