package social.alone.server.event.repository;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import social.alone.server.event.Event;
import social.alone.server.event.QEvent;
import social.alone.server.event.type.EventQueryParams;
import social.alone.server.user.User;

import java.util.List;

public class EventRepositoryImpl extends QuerydslRepositorySupport implements EventRepositoryCustom {

  public EventRepositoryImpl() {
    super(Event.class);
  }

  @Override
  public Page<Event> search(Pageable pageable, User user, EventQueryParams eventQueryParams) {
    QEvent event = QEvent.event;
    var query = from(event);
    switch (eventQueryParams.getType()) {
      case OWNER:
        if (user != null) {
          query = query.where(event.owner.eq(user));
        }
        break;
      case JOINER:
        if (user != null) {
          query = query.where(event.users.contains(user));
        }
        break;
      case ALL:
        break;
      default:
        throw new IllegalArgumentException("eventQueryParams.getType() can not be null");

    }

    // TODO Extract
    var coordinate = eventQueryParams.getCoordinate();
    if (coordinate.isPresent()) {
      var exactCoordinate = coordinate.get();
      query = query.orderBy(
              (
                      (event.location.longitude.subtract(exactCoordinate.getLongitude()).abs())
                              .add(
                                      (event.location.latitude).subtract(exactCoordinate.getLatitude()).abs())
              )
                      .asc()
      );
    }
    query = query.where(conditionalStartedAt(pageable, eventQueryParams));


    final List<Event> events = query
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    return new PageImpl<>(events, pageable, query.fetchCount());
  }

  @Override
  public Page<Event> search(Pageable pageable, EventQueryParams eventQueryParams) {
    return search(pageable, null, eventQueryParams);
  }

  private Predicate conditionalStartedAt(Pageable pageable, EventQueryParams params){
      if (params.getStartedAt() == null){
          return null;
      }
      return null;
  }
}
