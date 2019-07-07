package social.alone.server.event

import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import social.alone.server.event.dto.EventDto
import social.alone.server.event.repository.EventRepository
import social.alone.server.event.type.EventTypeRepository
import social.alone.server.location.Location
import social.alone.server.location.LocationRepository
import social.alone.server.user.User
import social.alone.server.user.UserRepository
import java.util.*

@Slf4j
@Service
@Transactional
class EventCreateSvc {

    @Autowired
    lateinit var eventRepository: EventRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var locationRepository: LocationRepository

    @Autowired
    lateinit var eventTypeRepository: EventTypeRepository


    fun create(eventDto: EventDto, user_: User): Event {
        val user: User? = userRepository.findById(user_.id).get()
        val location = getLocation(eventDto)
        val event = Event(eventDto, user, location)
        updateEventTypes(event, eventDto)
        return this.eventRepository.save(event)
    }


    private fun updateEventTypes(event: Event, eventDto: EventDto) {
        if (eventDto.eventTypes != null) {
            val eventIds = eventDto.eventTypes.map{
                it.id
            }
            val eventTypes = eventTypeRepository.findAllById(eventIds)
            event.eventTypes = HashSet(eventTypes)
        }
    }

    private fun getLocation(eventDto: EventDto): Location {
        val location = eventDto.getLocation()
        val by = locationRepository
                .findByLongitudeAndLatitudeAndName(
                        location.longitude!!,
                        location.latitude!!,
                        location.name
                )
        if (by.isPresent){
            return by.get()
        }
        return locationRepository.save(location)
    }

}
