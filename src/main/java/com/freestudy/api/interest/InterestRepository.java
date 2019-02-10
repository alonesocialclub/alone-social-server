package com.freestudy.api.interest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashSet;
import java.util.List;

public interface InterestRepository extends JpaRepository<Interest, Long> {

  HashSet<Interest> findAllByValueIn(List<String> values);

}
