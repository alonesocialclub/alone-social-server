package com.freestudy.api.interest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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


  public HashSet<Interest> saveAll(@NonNull List<InterestDto> interests) {
    var values = interests.stream().map(InterestDto::getValue).collect(Collectors.toList());

    HashSet<Interest> results = new HashSet<>(interestRepository.findAllByValueIn(values));

    if (results.size() == values.size()) {
      return results;
    }

    Set<String> existsValues = results.stream().map(Interest::getValue).collect(Collectors.toSet());

    HashSet<Interest> itemsToBeSaved = values
            .stream()
            .filter(value -> !existsValues.contains(value))
            .map(value -> Interest.builder().value(value).build())
            .collect(Collectors.toCollection(HashSet::new));

    results.addAll(interestRepository.saveAll(itemsToBeSaved));

    return results;

  }
}
