package com.freestudy.api.event;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Component
public class EventValidator {

  public void validate(EventDto eventDto, Errors errors) {
    if (eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() > 0) {
      // 무제한 경매 ( 높은 금액 낸 살마이 등록 ) 인 경우라 할 지어도, 0 원이면 안된다.
      errors.rejectValue("basePrice", "wrong", " 무제한 경매 ( 높은 금액 낸 살마이 등록 ) 인 경우라 할 지어도, 0 원이면 안된다.");
    }

    LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
    if (endEventDateTime.isBefore(eventDto.getBeginEventDateTime())) {
      errors.rejectValue("endEventDateTime", "wrong", "invalid endEventDateTime");
    }

  }
}
