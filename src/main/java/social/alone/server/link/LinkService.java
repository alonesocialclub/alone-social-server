package social.alone.server.link;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social.alone.server.event.Event;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LinkService {

  private final LinkRepository linkRepository;

  public Link createLink(Event event){
    Link link = event.createLink();
    return linkRepository.save(link);
  }
}
