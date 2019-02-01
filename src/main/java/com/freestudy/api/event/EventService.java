package com.freestudy.api.event;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EventService {

  private ModelMapper modelMapper;
  private EventRepository eventRepository;

  @Autowired
  public EventService(ModelMapper modelMapper, EventRepository eventRepository) {
    this.modelMapper = modelMapper;
    this.eventRepository = eventRepository;
  }

  public Event create(EventDto eventDto) {
    Event event = modelMapper.map(eventDto, Event.class);
    return this.eventRepository.save(event);
  }
}
