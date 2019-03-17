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

    return update(event, eventDto);
  }

  public Event update(Event event, EventDto eventDto) {
    eventDto = updatewtihlocation(eventDto);
    event = this.eventRepository.save(event);
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
    event.update(eventDto);
    return event;
  }

  private EventDto updatewtihlocation(EventDto eventDto){
   Location location = eventDto.getLocation();

    locationRepository
            .findByLongitudeAndLatitudeAndName(location.getLongitude(), location.getLatitude(), location.getName())
            .ifPresent(eventDto::setLocation);

    return eventDto;
  }

  public void delete(Integer eventId) {
    Optional<Event> event = this.eventRepository.findById(eventId);
    event.ifPresent(this.eventRepository::delete);
  }

  public Event joinEvent(Integer eventId, Long userId) {
    User user = this.userRepository.findById(userId).orElseThrow();
    var event = this.eventRepository.findById(eventId).orElseThrow();
    event.joinEvent(user);
    return this.eventRepository.save(event);
  }

  // MAKE DRY
  public Event joinEventCancel(Integer eventId, Long userId) {
    Event event = this.eventRepository.findById(eventId).orElseThrow();
    User user = this.userRepository.findById(userId).orElseThrow();
    event.joinCancelEvent(user);
    return this.eventRepository.save(event);
  }
}
