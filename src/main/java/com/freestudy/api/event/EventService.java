package com.freestudy.api.event;

import com.freestudy.api.event.type.EventType;
import com.freestudy.api.event.type.EventTypeDto;
import com.freestudy.api.event.type.EventTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventService {

  private EventRepository eventRepository;

  private EventTypeRepository eventTypeRepository;

  @Autowired
  public EventService(EventRepository eventRepository, EventTypeRepository eventTypeRepository) {
    this.eventRepository = eventRepository;
    this.eventTypeRepository = eventTypeRepository;
  }

  public Event create(EventDto eventDto) {
    Event event = new Event(eventDto);
    this.eventRepository.save(event);

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
}
