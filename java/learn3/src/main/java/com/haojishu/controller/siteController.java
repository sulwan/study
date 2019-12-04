package com.haojishu.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class siteController {

  @RequestMapping(value = "/")
  public String helo(HttpSession session) {
    String username = (String) session.getAttribute("username");
    if (username == null) {
      username = "sulwan";
    }
    session.setAttribute("username", username);
    return "hello redis";
  }
}
