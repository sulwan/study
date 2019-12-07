package com.haojishu.controller;

import com.haojishu.entity.User;
import com.haojishu.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class siteController {

  @Autowired UserServiceImpl userService;

  @RequestMapping(value = "/")
  public String hello() {
    User user = new User();
    user.setUsername("sulwan2");
    user.setEmail("sulwan@126.com");
    userService.save(user);
    List<User> list = userService.findAllWhereString("sulwan2");
    System.out.println(list);
    return "hello";
  }
}
