package social.alone.server.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social.alone.server.event.Event;
import social.alone.server.event.repository.EventRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventDeleteService {

    private final EventRepository eventRepository;

    public void delete(Long eventId) {
        Optional<Event> event = this.eventRepository.findById(eventId);
        event.ifPresent(this.eventRepository::delete);
    }
}
