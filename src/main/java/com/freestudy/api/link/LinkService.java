package com.freestudy.api.link;


import com.freestudy.api.event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class LinkService {

  private LinkRepository linkRepository;

  @Autowired
  public LinkService(LinkRepository linkRepository){

    this.linkRepository = linkRepository;
  }

  public Link createLink(Event event){
    Link link = event.createLink();
    return linkRepository.save(link);
  }
}
