package social.alone.server.push;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import social.alone.server.auth.oauth2.user.CurrentUser;
import social.alone.server.user.User;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Controller
@RequestMapping(value = "/api/push")
@RequiredArgsConstructor
public class PushController {


    @PostMapping("/tokens")
    public ResponseEntity enrollFcmToken(
            @RequestBody @Valid @NotEmpty String fcmToken,
            @CurrentUser User user
    ) {

        // See documentation on defining a message payload.
        Message message = Message.builder()
                .putData("score", "850")
                .putData("time", "2:45")
                .setNotification(new Notification("title~", "body~"))
                .setToken(fcmToken)
                .build();


        try {
            String response = FirebaseMessaging.getInstance().send(message);
            return ResponseEntity.ok(response);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }
}
