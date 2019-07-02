package social.alone.server.push;


import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;

@Service
public class PushSenderSvc {

    public void send(String fcmToken) {
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
                .setToken(fcmToken)
                .build();


        try {
            String response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }
}
