package com.igor.boardingmanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Application {

  @GetMapping()
  public String home() {
    return "Hello Docker World";
  }

}
