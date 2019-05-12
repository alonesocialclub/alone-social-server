package social.alone.server.event;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import social.alone.server.event.dto.EventDto;

@Component
public class EventValidator {

  public void validate(EventDto eventDto, Errors errors) {

    if (
            eventDto.getStartedAt() != null &&
            eventDto.getStartedAt().isAfter(eventDto.getEndedAt())) {
      errors.reject(
              "Wrong event.startedAt with endedAt",
              "이벤트 시작 시간은 종료시간보다 뒤 일 수 없습니다."
      );
    }
  }
}
