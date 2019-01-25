package com.freestudy.api.interest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class InterestService {

  private InterestRepository interestRepository;

  @Autowired
  public InterestService(InterestRepository interestRepository) {
    this.interestRepository = interestRepository;
  }


  public Set<Interest> saveAll(List<InterestDto> interests) {
    var values = interests.stream().map(InterestDto::getValue).collect(Collectors.toList());

    Set<Interest> results = new HashSet<>(interestRepository.findAllByValueIn(values));

    if (results.size() == values.size()) {
      return results;
    }

    Set<String> existsValues = results.stream().map(Interest::getValue).collect(Collectors.toSet());

    List<Interest> itemsToBeSaved = values
            .stream()
            .filter(value -> !existsValues.contains(value))
            .map(value -> Interest.builder().value(value).build())
            .collect(Collectors.toList());

    results.addAll(interestRepository.saveAll(itemsToBeSaved));

    return results;

  }
}
