package social.alone.server.event

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import social.alone.server.event.repository.EventRepository
import social.alone.server.event.service.EventSearchService
import social.alone.server.event.type.EventQueryParams

@RunWith(MockitoJUnitRunner::class)
class EventSearchServiceTest{

    @InjectMocks
    lateinit var eventSearchService: EventSearchService

    @Mock
    lateinit var  eventRepository: EventRepository

    @Test
    fun findAllBy() {

        val pageable = PageRequest.of(0, 10)
        val eventQueryParams = EventQueryParams(
                37.4991561765984, 127.026503385182
        )

        given(
                eventRepository.search(pageable, eventQueryParams)
        ).willReturn(Page.empty())

        val results = eventSearchService.findAllBy(pageable, eventQueryParams)

        assertThat(results.isEmpty).isTrue()
        verify(eventRepository, times(1)).search(pageable, eventQueryParams)
    }
}