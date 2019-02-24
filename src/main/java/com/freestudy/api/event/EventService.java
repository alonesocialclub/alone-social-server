package com.freestudy.api.event;

import com.freestudy.api.event.type.EventTypeDto;
import com.freestudy.api.event.type.EventTypeRepository;
import com.freestudy.api.user.User;
import com.freestudy.api.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventService {

  private EventRepository eventRepository;

  private UserRepository userRepository;

  private EventTypeRepository eventTypeRepository;

  public EventService(EventRepository eventRepository, EventTypeRepository eventTypeRepository, UserRepository userRepository) {
    this.eventRepository = eventRepository;
    this.userRepository = userRepository;
    this.eventTypeRepository = eventTypeRepository;
  }

  public Event create(EventDto eventDto, User user_) {
    User user = userRepository.findById(user_.getId()).orElseThrow();
    Event event = new Event(eventDto, user);
    return update(event, eventDto);
  }

  public Event update(Event event, EventDto eventDto) {
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

  public Page<Event> findAll(Pageable pageable) {
    return this.eventRepository.findByEndedAtAfter(LocalDateTime.now(), pageable);
  }

  public Event joinEvent(Integer eventId, Long userId) {
    Event event = this.eventRepository.findById(eventId).orElseThrow();
    User user = this.userRepository.findById(userId).orElseThrow();
    event.joinEvent(user);
    return event;
  }

  // MAKE DRY
  public Event joinEventCancel(Integer eventId, Long userId) {
    Event event = this.eventRepository.findById(eventId).orElseThrow();
    User user = this.userRepository.findById(userId).orElseThrow();
    event.joinCancelEvent(user);
    return event;
  }
}
