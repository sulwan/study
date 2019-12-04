package com.haojishu.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PropertySource(
    value = "classpath:sulwan.properties",
    ignoreResourceNotFound = false,
    encoding = "UTF-8")
public class siteController {

  @Value("${sulwan}")
  private String username;

  @RequestMapping(value = "/")
  public String hello() {
    System.out.println(username);
    return "hello properties";
  }
}
