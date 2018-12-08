package com.freestudy.api.index;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

  @GetMapping("/")
  public String getIndex() {
    return "Hello world";
  }
}
