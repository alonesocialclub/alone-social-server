package com.freestudy.api.event;

import com.freestudy.api.BaseDaoTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class EventTypeRepositoryTest extends BaseDaoTest {

  @Autowired
  EventTypeRepository eventTypeRepository;

  @Test
  public void saveTest() throws Exception {

    // given
    EventType eventType = EventType.of("스타트업");
    // when
    eventTypeRepository.save(eventType);

    // then
    assertThat(eventType).isNotNull();
    assertThat(eventType.getEvents()).isEmpty();
  }

  @Test
  public void findAllByValueInTest() throws Exception {
//    // given
    Set<EventType> eventTypes = new HashSet<>();
    eventTypes.add(EventType.of("과학"));
    eventTypes.add(EventType.of("make the world a better place"));
    eventTypes.add(EventType.of("통계"));
    eventTypeRepository.saveAll(eventTypes);
    List<String> eventTypesKeywords = eventTypes.stream().map(EventType::getValue).collect(Collectors.toList());

    // when
    List<EventType> resultList = eventTypeRepository.findAllByValueIn(eventTypesKeywords);
    Set<EventType> results = new HashSet<>(resultList);

    // then
    assertThat(results).isEqualTo(eventTypes);
  }

}