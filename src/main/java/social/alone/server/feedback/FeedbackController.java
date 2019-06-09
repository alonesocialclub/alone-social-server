package social.alone.server.feedback;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    @PostMapping
    public ResponseEntity postFeedBack(){

        return ResponseEntity.noContent().build();
    }
}
