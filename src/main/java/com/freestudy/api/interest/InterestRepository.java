package com.freestudy.api.interest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestRepository extends JpaRepository<Interest, Long> {


  List<Interest> findAllByValueIn(List<String> values);

}
