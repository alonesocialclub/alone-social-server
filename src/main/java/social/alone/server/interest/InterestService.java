package social.alone.server.interest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class InterestService {

  private final InterestRepository interestRepository;


  public HashSet<Interest> saveAll(@NonNull List<InterestDto> interests) {
    List<String> values = interests.stream().map(InterestDto::getValue).collect(Collectors.toList());

    HashSet<Interest> results = new HashSet<>(interestRepository.findAllByValueIn(values));

    if (results.size() == values.size()) {
      return results;
    }

    Set<String> existsValues = results.stream().map(Interest::getValue).collect(Collectors.toSet());

    HashSet<Interest> itemsToBeSaved = values
            .stream()
            .filter(value -> !existsValues.contains(value))
            .map(Interest::new)
            .collect(Collectors.toCollection(HashSet::new));

    results.addAll(interestRepository.saveAll(itemsToBeSaved));

    return results;

  }
}
