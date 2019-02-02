package com.freestudy.api.link;

import com.freestudy.api.event.Event;
import com.freestudy.api.event.EventRepository;
import com.freestudy.api.fixture.EventFixture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LinkServiceTest {

  @Autowired
  private LinkService linkService;

  @Autowired
  private EventRepository eventRepository;

  private EventFixture eventFixture;

  @Before
  public void setUp() {
    eventFixture = new EventFixture(eventRepository);
  }

  @Test
  public void createLinkTest() {
    // Given
    Event event = eventFixture.build();

    // When
    Link link = linkService.createLink(event);

    // Then
    assertThat(link.getEvent()).isEqualTo(event);
    assertThat(link.getId()).isNotNull();
  }
}