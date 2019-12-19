package com.haojishu.controller;

import com.haojishu.annotate.SulwanLog;
import com.haojishu.entity.User;
import com.haojishu.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class siteController {

  @Autowired UserServiceImpl userService;

  @GetMapping(value = "/")
  @SulwanLog("这是测试日志拦截的")
  public String hello(String hello) {
    User user = new User();
    user.setUsername("测试数据");
    userService.add(user);
    return "添加成功";
  }
}
