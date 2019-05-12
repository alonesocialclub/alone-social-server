package social.alone.server.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import social.alone.server.event.Event;
import social.alone.server.event.dto.EventDto;
import social.alone.server.event.repository.EventRepository;
import social.alone.server.event.type.EventTypeDto;
import social.alone.server.event.type.EventTypeRepository;
import social.alone.server.location.Location;
import social.alone.server.location.LocationRepository;
import social.alone.server.user.User;
import social.alone.server.user.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EventService {

  private final EventRepository eventRepository;

  private final UserRepository userRepository;

  private final EventTypeRepository eventTypeRepository;

  private final LocationRepository locationRepository;

  public Event create(EventDto eventDto, User user_) {
    User user = userRepository.findById(user_.getId()).orElseThrow();
    Location location = getLocation(eventDto);
    Event event = new Event(eventDto, user, location);
    updateEventTypes(event, eventDto);
    return this.eventRepository.save(event);
  }

  public Event update(Event event, EventDto eventDto){
    Location location = getLocation(eventDto);
    updateEventTypes(event, eventDto);
    event.updateLocation(location);
    event.updateByEventDto(eventDto);
    return this.eventRepository.save(event);
  }

  private void updateEventTypes(Event event, EventDto eventDto) {
    if (eventDto.getEventTypes() != null) {
      var eventTypes = eventTypeRepository.findAllById(
              eventDto
                      .getEventTypes()
                      .stream()
                      .map(EventTypeDto::getId)
                      .collect(Collectors.toSet())
      );
      event.setEventTypes(new HashSet<>(eventTypes));
    }
  }

  private Location getLocation(EventDto eventDto) {
    Location location = eventDto.getLocation();
    var by = locationRepository
            .findByLongitudeAndLatitudeAndName(
                    location.getLongitude(),
                    location.getLatitude(),
                    location.getName()
            );
    return by.orElseGet(() -> locationRepository.save(location));
  }

  public void delete(Long eventId) {
    Optional<Event> event = this.eventRepository.findById(eventId);
    event.ifPresent(this.eventRepository::delete);
  }

  public Event joinEvent(Long eventId, Long userId) {
    User user = this.userRepository.findById(userId).orElseThrow();
    var event = this.eventRepository.findById(eventId).orElseThrow();
    event.joinEvent(user);
    return this.eventRepository.save(event);
  }

  // MAKE DRY
  public Event joinEventCancel(Long eventId, Long userId) {
    Event event = this.eventRepository.findById(eventId).orElseThrow();
    User user = this.userRepository.findById(userId).orElseThrow();
    event.joinCancelEvent(user);
    return this.eventRepository.save(event);
  }
}
