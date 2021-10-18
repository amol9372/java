package com.expensetracker.trackerapp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

  @Value("${server.port}")
  private int serverPort;

  @GetMapping("health-check")
  public String healthCheck(){
     return "Application is Running on -> ".concat(String.valueOf(serverPort));
  }

}
