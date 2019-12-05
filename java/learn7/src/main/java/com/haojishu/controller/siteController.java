package com.haojishu.controller;

import com.haojishu.annotate.Log;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class siteController {

  @Log(value = "111111111111111111111111111111111111111")
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String hello(String username, String password) {
    return "hello world";
  }
}
