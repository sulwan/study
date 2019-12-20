package com.haojishu.controller;

import com.haojishu.annotate.SulwanForm;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class siteController {

  @GetMapping(value = "/sulwan")
  @SulwanForm
  public String hello() {
    System.out.println("hello");
    return "hello";
  }
}
