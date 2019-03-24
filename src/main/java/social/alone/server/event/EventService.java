package social.alone.server.event;

import social.alone.server.event.repository.EventRepository;
import social.alone.server.event.type.EventTypeDto;
import social.alone.server.event.type.EventTypeRepository;
import social.alone.server.location.Location;
import social.alone.server.location.LocationRepository;
import social.alone.server.user.User;
import social.alone.server.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventService {

  private EventRepository eventRepository;

  private UserRepository userRepository;

  private EventTypeRepository eventTypeRepository;

  private LocationRepository locationRepository;


  public EventService(EventRepository eventRepository, EventTypeRepository eventTypeRepository, UserRepository userRepository, LocationRepository locationRepository) {
    this.eventRepository = eventRepository;
    this.userRepository = userRepository;
    this.eventTypeRepository = eventTypeRepository;
    this.locationRepository = locationRepository;
  }

  public Event create(EventDto eventDto, User user_) {
    User user = userRepository.findById(user_.getId()).orElseThrow();
    Event event = new Event(eventDto, user);
    event.update(eventDto);
    updateLocation(event, eventDto);
    updateEventTypes(event, eventDto);
    return this.eventRepository.save(event);
  }

  public Event update(Event event, EventDto eventDto) {
    event.update(eventDto);
    updateLocation(event, eventDto);
    updateEventTypes(event, eventDto);
    return this.eventRepository.save(event);
  }

  private void updateLocation(Event event, EventDto eventDto) {
    Location location = getLocation(eventDto);
    event.update(location);
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
    return locationRepository
            .findByLongitudeAndLatitudeAndName(location.getLongitude(), location.getLatitude(), location.getName())
            .orElse(location);

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
