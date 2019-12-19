package com.haojishu.controller;

import com.haojishu.exception.UserCustException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class siteController {

  @GetMapping(value = "/")
  public String hello() {
    throw new RuntimeException("错误页面显示");
  }

  @GetMapping(value = "/hello")
  public String Hello2() {
    throw new UserCustException("baidu.com", "自己吃饱了撑的");
  }
}
