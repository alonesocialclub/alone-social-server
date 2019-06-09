package social.alone.server.feedback;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
class FeedbackRequest {

    @NotEmpty
    private String text;
}
