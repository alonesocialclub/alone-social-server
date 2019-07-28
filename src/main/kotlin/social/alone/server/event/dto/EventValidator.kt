package social.alone.server.event.dto


import org.springframework.stereotype.Component
import org.springframework.validation.Errors

@Component
class EventValidator {

    fun validate(eventDto: EventDto, errors: Errors) {

        if (eventDto.startedAt != null && eventDto.startedAt!!.isAfter(eventDto.endedAt!!)) {
            errors.reject(
                    "Wrong event.startedAt with endedAt",
                    "이벤트 시작 시간은 종료시간보다 뒤 일 수 없습니다."
            )
        }
    }
}
