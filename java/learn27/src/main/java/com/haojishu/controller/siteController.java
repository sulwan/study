package com.haojishu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class siteController {

  @GetMapping(value = "/")
  public String hello() {
    System.out.println("hello");
    return "hello";
  }
}
