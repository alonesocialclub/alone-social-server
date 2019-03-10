package com.freestudy.api.event;

import com.freestudy.api.event.type.EventQueryType;
import com.freestudy.api.user.User;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
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
          Optional<User> user,
          EventQueryParams eventQueryParams
  ) {
    // TODO make query builder?
    if (user.isEmpty() || eventQueryParams.getType().equals(EventQueryType.ALL)) {
      return this.eventRepository.findAll(pageable);
    }

    switch (eventQueryParams.getType()) {
      case OWNER:
        return this.eventRepository.findByOwner(user.get(), pageable);
      case JOINER:
        return this.eventRepository.findByUsersContaining(user.get(), pageable);
      default:
        throw new IllegalIdentifierException("Invalid query parameter for type, type=OWNER|ALL|JOINER");
    }
  }
}
