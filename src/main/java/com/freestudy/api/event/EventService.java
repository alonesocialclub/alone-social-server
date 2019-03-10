package com.freestudy.api.event;

import com.freestudy.api.event.type.EventQueryType;
import com.freestudy.api.event.type.EventTypeDto;
import com.freestudy.api.event.type.EventTypeRepository;
import com.freestudy.api.location.Location;
import com.freestudy.api.location.LocationRepository;
import com.freestudy.api.user.User;
import com.freestudy.api.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

  private EventDto updatewtihlocation(EventDto eventDto) {
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

  public Page<Event> findAll(Pageable pageable, User user, Optional<EventQueryType> type) {
    // TODO make query builder?
    if (user == null) {
      return this.eventRepository.findByEndedAtAfter(LocalDateTime.now(), pageable);
    }
    switch (type.get()) {
      case OWNER:
        return this.eventRepository.findByOwnerAndEndedAtAfter(user, LocalDateTime.now(), pageable);
      case JOINER:
        return this.eventRepository.findByUsersContainingAndEndedAtAfter(user, LocalDateTime.now(), pageable);
      default:
        return this.eventRepository.findByEndedAtAfter(LocalDateTime.now(), pageable);
    }
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
