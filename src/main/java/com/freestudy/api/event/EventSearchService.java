package com.freestudy.api.event;

import com.freestudy.api.event.type.EventQueryType;
import com.freestudy.api.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EventSearchService {

  private EventRepository eventRepository;


  public EventSearchService(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  public Page<Event> findAllBy(
          Pageable pageable,
          User user,
          Optional<EventQueryType> type
  ) {
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
}
