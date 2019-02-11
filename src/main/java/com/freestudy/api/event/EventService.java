package com.freestudy.api.event;

import com.freestudy.api.event.type.EventTypeDto;
import com.freestudy.api.event.type.EventTypeRepository;
import com.freestudy.api.user.User;
import com.freestudy.api.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventService {

  private EventRepository eventRepository;

  private UserRepository userRepository;

  private EventTypeRepository eventTypeRepository;

  @Autowired
  public EventService(EventRepository eventRepository, UserRepository userRepository, EventTypeRepository eventTypeRepository) {
    this.eventRepository = eventRepository;
    this.userRepository = userRepository;
    this.eventTypeRepository = eventTypeRepository;
  }

  public Event create(EventDto eventDto, User user_) {
    User user = userRepository.findById(user_.getId()).orElseThrow();
    Event event = new Event(eventDto, user);
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

    return event;
  }

  public Event update(Event event, EventDto eventDto) {
    event.update(eventDto);
    return event;
  }

  public Page<Event> findAll(Pageable pageable) {
    return this.eventRepository.findAll(pageable);
  }
}
