package com.freestudy.api.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface EventRepository extends JpaRepository<Event, Integer> {

  Page<Event> findByEndedAtAfter(LocalDateTime endedAtAfter, Pageable pageable);
}
